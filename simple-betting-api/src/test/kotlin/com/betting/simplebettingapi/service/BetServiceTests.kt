package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.BetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class BetServiceTests {

    private val rollService: RollService = mockk()
    private val walletService: WalletService = mockk()

    private val betRepository: BetRepository = mockk()
    private val accountRepository: AccountRepository = mockk()

    private val betService =
        BetServiceImpl(rollService, walletService, betRepository, accountRepository)

    private val betModel1 = BetModel(
        BigDecimal(100),
        5,
        BetStatus.PLACED, Instant.now(),
        RollModel(Instant.now(), 0),
        mockk()
    )

    private val betModel2 = BetModel(
        BigDecimal(500),
        1,
        BetStatus.WON_HALF, Instant.now(),
        RollModel(
            Instant.now().minus(5, ChronoUnit.MINUTES),
            3
        ),
        mockk()
    )


    @Test
    fun givenAccountId_WhenAccountExists_ReturnAccountBets() {
        //given
        every {
            betRepository.findAllByAccountIdOrderByPlacedDtDesc(any())
        } returns listOf(betModel1, betModel2)

        //when
        val betListDto = betService.getBetsByAccountId(1)

        //then
        assertEquals(2, betListDto.bets.size)

        val betDto1 = betListDto.bets[0]
        assertEquals(betModel1.amount, betDto1.betAmount)
        assertEquals(betModel1.numberBetOn, betDto1.numberBetOn)
        assertEquals(betModel1.status, betDto1.status)

        val betDto2 = betListDto.bets[1]
        assertEquals(betModel2.amount, betDto2.betAmount)
        assertEquals(betModel2.numberBetOn, betDto2.numberBetOn)
        assertEquals(betModel2.status, betDto2.status)
    }

    @Test
    fun givenBetId_WhenBetExists_BetDtoIsReturned() {
        //given
        every { betRepository.findById(any()) } returns Optional.of(betModel2)

        //when
        val betDto = betService.getBetById(1)

        //then
        assertEquals(betModel2.numberBetOn, betDto.numberBetOn )
        assertEquals(betModel2.placedDt, betDto.placedDt )
        assertEquals(betModel2.status, betDto.status )
        assertEquals(betModel2.roll.number, betDto.roll!!.numberRolled )
    }

    @Test
    fun givenAccountIdAndBetDto_WhenPlaceBetIsCalled_BetIsPlaced() {
        //given
        val walletModel = WalletModel(BigDecimal(1000))
        val accountModel: AccountModel =
            AccountModel("username", "name", "surname", walletModel)
        walletModel.account = accountModel

        val betDto = BetDto(
            betModel1.amount,
            betModel1.numberBetOn,
            null,
            BetStatus.PLACED,
            betModel1.placedDt,
            mockk()
        )

        every { accountRepository.findById(any()) } returns Optional.of(accountModel)
        every { betRepository.save(any()) } returns betModel1
        every { rollService.getCurrentRoll() } returns mockk()
        every { walletService.updateBalance(any(), any(), any()) } returns mockk()

        //when
        betService.placeBet(1, betDto)

        //then
        verify {
            betRepository.save(withArg {
                assertEquals(betModel1.amount, it.amount)
                assertEquals(betModel1.numberBetOn, it.numberBetOn)
                assertEquals(BetStatus.PLACED, it.status)
            })
        }

        val targetBalance = walletModel.balance.subtract(betDto.betAmount)

        verify {
            walletService.updateBalance(withArg {
                assertEquals(walletModel, it)
                assertEquals(accountModel, it.account)
            }, withArg {
                assertEquals(targetBalance, it)
            }, withArg {
                assertEquals(TransactionType.BET_PLACEMENT, it)
            })
        }

    }
}