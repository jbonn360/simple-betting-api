package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BetServiceImpl(
    @Autowired private val rollService: RollService,
    @Autowired private val betRepository: BetRepository,
    @Autowired private val accountRepository: AccountRepository,
) : BetService {
    override fun getBetsByAccountId(accountId: Int): BetListDto {
        val betModels = betRepository.findAllByAccount_IdOrderByPlacedDtDesc(accountId)
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

        val bet = BetModel(
            betDto.betAmount,
            Instant.now(),
            betDto.numberBetOn,
            betDto.status,
            rollService.getNextRoll(),
            accountModel.get()
        )

        val betSaved = betRepository.save(bet)

        return betSaved.id
    }
}