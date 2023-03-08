package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.helpers.Utils
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.BetRepository
import com.betting.simplebettingapi.repository.RollRepository
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal
import java.time.Instant

//@DataJpaTest
class RollServiceTests() {
    //@Autowired
    //lateinit var entityManager: TestEntityManager

    // mock required classes
    private val walletService: WalletService = mockk()

    private val betRepository: BetRepository = mockk()
    private val rollRepository: RollRepository = mockk()

    private val utils: Utils = mockk()

    // create roll service impl instance with these mocks as parameters
    private lateinit var rollService: RollServiceImpl

    @Test
    fun givenWinningCondition_WhenNumberIsRolled_AccountWinsBet() {
        //given
        val rollModel = RollModel(Instant.now(), 5)
        val walletModel = WalletModel(BigDecimal.ZERO)
        val accountModel = AccountModel("username", "name", "surname", walletModel)

        walletModel.account = accountModel

        val betModel = BetModel(
            BigDecimal(50), 5, BetStatus.PLACED, Instant.now(), rollModel,
            accountModel
        )

        val betModelWon10 = betModel
        betModelWon10.status = BetStatus.WON_10

        every { rollRepository.save(any()) } returns rollModel

        rollService = RollServiceImpl(walletService, rollRepository, betRepository, utils, 1)

        //every { rollService.createAndPersistNewRollEntity() } returns rollModel
        every { betRepository.findAllByRoll(any()) } returns listOf(betModel)
        every { utils.generateRandomNumberRoll() } returns 5
        every { betRepository.save(betModel) } returns betModelWon10
        every { walletService.updateBalance(any(), any(), any()) } returns mockk()

        // when
        rollService.rollNextNumber()

        // then
        // roll entity is updated
        rollModel.number = 5
        verify { rollRepository.save(rollModel)}

        // bet entity is updated
        betModel.status = BetStatus.WON_10
        verify{ betRepository.save(betModel) }

        // balance is updated
        verify { walletService.updateBalance(walletModel, BigDecimal(500), TransactionType.BET_WIN) }
    }
}