package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class BetServiceImpl(
    @Autowired private val rollService: RollService,
    @Autowired private val walletService: WalletService,
    @Autowired private val betRepository: BetRepository,
    @Autowired private val accountRepository: AccountRepository,
) : BetService {
    private val logger = KotlinLogging.logger {}
    override fun getBetsByAccountId(accountId: Long): BetListDto {
        val betModels = betRepository.findAllByAccountIdOrderByPlacedDtDesc(accountId)
        val bets = ArrayList<BetDto>()

        //mapping bet model to bet dto
        betModels.forEach { betModel -> bets.add(Utils.mapBetModelToDto(betModel)) }

        return BetListDto(bets)
    }

    @Throws(EntityNotFoundException::class, InsufficientCreditsException::class)
    @Transactional
    override fun placeBet(accountId: Long, betDto: BetDto): Long {
        val account = accountRepository.findById(accountId)

        if(account.isEmpty){
            logger.error { "Error occurred while retrieving account with id $accountId to place bet" }
            throw EntityNotFoundException("Account with id $accountId was not found")
        }

        if(account.get().wallet.balance < betDto.betAmount)
            throw InsufficientCreditsException("Bet placement failed due to insufficient credits")

        val bet = BetModel(
            betDto.betAmount,
            betDto.numberBetOn,
            BetStatus.PLACED,
            Instant.now(),
            rollService.getCurrentRoll(),
            account.get()
        )

        val betSaved = betRepository.save(bet)

        val newBalance = account.get().wallet.balance.subtract(betDto.betAmount)
        walletService.updateBalance(account.get().wallet, newBalance, TransactionType.BET_PLACEMENT)

        return betSaved.id
    }
}