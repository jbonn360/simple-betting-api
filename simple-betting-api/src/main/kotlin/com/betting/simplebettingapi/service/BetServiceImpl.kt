package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class BetServiceImpl(
    @Autowired private val rollService: RollService,
    @Autowired private val betRepository: BetRepository,
    @Autowired private val accountRepository: AccountRepository,
) : BetService {
    override fun getBetsByAccountId(accountId: Int): BetListDto {
        val betModels = betRepository.findAllByAccountIdOrderByPlacedDtDesc(accountId)
        val bets = ArrayList<BetDto>()

        //mapping bet model to bet dto
        betModels.forEach { betModel -> bets.add(Utils.mapBetModelToDto(betModel)) }

        return BetListDto(bets)
    }

    override fun getBetById(betId: Int): BetDto {
        val betModel = betRepository.findById(betId)

        if (betModel.isPresent)
            return Utils.mapBetModelToDto(betModel.get())
        else
            throw EntityNotFoundException("Bet object with id $betId was not found")
    }

    override fun placeBet(accountId: Int, betDto: BetDto): Int {
        val accountModel = accountRepository.findById(accountId)

        //todo: replace with proper kotlin null handling
        if(accountModel.isEmpty)
            throw EntityNotFoundException("Account with id $accountId was not found")

        val betDto1 = BetDto(
            BigDecimal(12), 4
        )

        val bet = BetModel(
            betDto.betAmount,
            betDto.numberBetOn,
            BetStatus.PLACED,
            Instant.now(),
            rollService.getNextRoll(),
            accountModel.get()
        )

        val betSaved = betRepository.save(bet)

        return betSaved.id
    }
}