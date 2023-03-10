package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.helpers.UtilsTests
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.BetRepository
import com.betting.simplebettingapi.repository.RollRepository
import io.mockk.every
import org.junit.jupiter.api.Test

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import java.time.Instant

class RollServiceTests() {
    // mock required classes
    private val walletService: WalletService = mockk()

    private val betRepository: BetRepository = mockk()
    private val rollRepository: RollRepository = mockk()

    private val utils: Utils = mockk()

    // create roll service impl instance - class to be tested
    private lateinit var rollService: RollServiceImpl

    @Test
    fun givenWinningCondition_WhenNumberIsRolled_AccountWinsBet() {
        //given
        val numberBetOn: Byte = 5

        val rollModel = RollModel(Instant.now(), 0)
        val walletModel = WalletModel(BigDecimal.ZERO)
        val accountModel = AccountModel("username", "name", "surname", walletModel)

        walletModel.account = accountModel

        val betModel = BetModel(
            BigDecimal(50), numberBetOn, BetStatus.PLACED, Instant.now(), rollModel, accountModel
        )

        betModel.status = BetStatus.WON_10
        every { rollRepository.save(any()) } returns rollModel
        rollService = RollServiceImpl(walletService, rollRepository, betRepository, utils, 1)

        //every { rollService.createAndPersistNewRollEntity() } returns rollModel
        every { betRepository.findAllByRoll(any()) } returns listOf(betModel)
        every { utils.generateRandomNumberRoll() } returns numberBetOn
        every { betRepository.save(betModel) } returns betModel
        every { walletService.updateBalance(any(), any(), any()) } returns mockk()

        // when
        rollService.rollNextNumber()

        // then
        // roll entity is updated
        rollModel.number = numberBetOn
        verify { rollRepository.save(withArg { assertEquals(rollModel, it) }) }

        // bet entity is updated
        verify { betRepository.save(withArg { assertEquals(BetStatus.WON_10, it.status) }) }

        // winner is rewarded
        verify {
            walletService.updateBalance(withArg { assertEquals(walletModel, it) },
                withArg { assertEquals(BigDecimal(500), it) },
                withArg { assertEquals(TransactionType.BET_WIN, it) }
            )
        }
    }

    @Test
    fun givenNoBetsFound_WhenNumberIsRolled_RollIsPostponed() {
        //given
        val rollInterval: Byte = 1
        val rollModel = RollModel(Instant.now(), 5)
        every { rollRepository.save(any()) } returns rollModel
        rollService = RollServiceImpl(walletService, rollRepository, betRepository, utils, rollInterval)

        every { betRepository.findAllByRoll(any()) } returns emptyList()

        //when
        rollService.rollNextNumber()

        //then
        verify(atMost = 2) { rollRepository.save(any()) }
        verify(exactly = 0) { utils.generateRandomNumberRoll() }
    }
}

