package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.repository.BetRepository
import com.betting.simplebettingapi.repository.RollRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Service
class RollServiceImpl(
    @Autowired private val walletService: WalletService,
    @Autowired private val rollRepository: RollRepository,
    @Autowired private val betRepository: BetRepository,
    @Value("\${app.roll.interval-mins}") private val rollInterval: Byte
) : RollService {
    private var currentRoll: RollModel = createAndPersistNewRollEntity()
    private val logger = KotlinLogging.logger {}

    final fun createAndPersistNewRollEntity(): RollModel {
        val roll = RollModel(Instant.now().plus(rollInterval.toLong(), ChronoUnit.MINUTES))
        return rollRepository.save(roll)
    }

    override fun getCurrentRoll(): RollModel {
        return currentRoll
    }

    // roll a new number every x minutes
    @Scheduled(
        fixedRateString = "\${app.roll.interval-mins}",
        initialDelayString = "\${app.roll.interval-mins}",
        timeUnit = TimeUnit.MINUTES,
    )
    fun rollNextNumber() {
        //1. if there are no bets on current roll, postpone the roll
        val bets = betRepository.findAllByRoll(this.currentRoll)
        if (bets.isEmpty()) {
            logger.info { "No bets found, postponing roll..." }
            currentRoll = postponeRoll(currentRoll, rollInterval.toLong())
            return
        }

        // 2. perform operations to close the current round
        // a. generate the current roll result
        val curRollResult = Utils.generateRandomNumberRoll()
        logger.info { "Rolled new number '$curRollResult'" }

        // b. update bet entities associated with current bet and wallet
        //    balances for wallets that won
        updateBetsAndRewardWinners(bets, curRollResult)

        // c. update roll entity for current roll in db
        this.currentRoll.number = curRollResult
        rollRepository.save(this.currentRoll)

        // 3. generate model for the next round and save it to db
        this.currentRoll = createAndPersistNewRollEntity()
    }

    fun postponeRoll(roll: RollModel, durationMins: Long): RollModel {
        roll.rollDt = roll.rollDt.plus(durationMins, ChronoUnit.MINUTES)
        return rollRepository.save(roll)
    }

    fun updateBetsAndRewardWinners(bets: List<BetModel>, curRollResult: Byte) {
        for (bet in bets) {
            // check if bet won or lost
            val betResult = Utils.classifyBetResult(bet.numberBetOn, curRollResult)

            // update bet entity in database
            bet.status = betResult
            betRepository.save(bet)

            // credit wallet if won
            if (BetStatus.isWinningStatus(betResult)) {
                val creditsWon = Utils.calculatePrize(bet.amount, betResult)
                val wallet = bet.account.wallet

                walletService.updateBalance(wallet, wallet.balance.add(creditsWon))
            }
        }
    }
}