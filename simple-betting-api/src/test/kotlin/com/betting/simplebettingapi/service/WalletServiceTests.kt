package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.exception.InvalidTransactionException
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.WalletRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class WalletServiceTests {

    private val transactionService: TransactionService = mockk()
    private val walletRepository: WalletRepository = mockk()

    private val walletService = WalletServiceImpl(transactionService, walletRepository)

    @Test
    fun givenAccountId_WhenAccountExists_ThenWalletIsReturned() {
        //given
        val walletModel = WalletModel(BigDecimal(100))

        every { walletRepository.findByAccountId(1) } returns Optional.of(walletModel)

        //when
        val walletModelReturned = walletService.getWalletByAccountId(1)

        //then
        assertEquals(walletModel, walletModelReturned)
    }

    @Test
    fun givenWalletAndNewBalanceAndTransactionType_WhenUpdateBalanceCalled_BalanceUpdated() {
        //given
        val walletModel = WalletModel(BigDecimal(100))

        val transactionAmount = BigDecimal(400)
        val transactionType = TransactionType.BET_WIN

        val newBalance = walletModel.balance.plus(transactionAmount)

        every {
            transactionService.createTransaction(
                any(),
                walletModel
            )
        } returns mockk()

        //when
        walletService.updateBalance(walletModel, newBalance, transactionType)

        //then
        assertEquals(newBalance, walletModel.balance)

        verify{
            transactionService.createTransaction(
                withArg {
                    assertEquals(transactionType, it.transactionType)
                    assertEquals(transactionAmount, it.amount)
                },
                withArg {
                    assertEquals(walletModel, it)
                }
            )
        }
    }

    @Test
    fun givenWalletAndNewBalanceAndTransactionType_WhenBalanceLessThanZero_ThenErrorIsShownAndBalanceNotUpdated() {
        //given
        val oldBalance = BigDecimal(100)
        val walletModel = WalletModel(oldBalance)
        val transactionAmount = BigDecimal(-200)
        val transactionType = TransactionType.BET_PLACEMENT

        val newBalance = walletModel.balance.plus(transactionAmount)

        //when
        assertThrows<InsufficientCreditsException> {
            walletService.updateBalance(walletModel, newBalance, transactionType)
        }

        //then
        verify(exactly = 0) {
            transactionService.createTransaction(any(), any())
        }

        assertEquals(oldBalance, walletModel.balance)
    }

    @Test
    fun givenWalletAndNewBalanceAndTransactionType_WhenTransactionAmountIsZero_ThenErrorIsShownAndBalanceNotUpdated() {
        //given
        val oldBalance = BigDecimal(100)
        val walletModel = WalletModel(oldBalance)
        val transactionAmount = BigDecimal(0)
        val transactionType = TransactionType.BET_PLACEMENT

        val newBalance = walletModel.balance.plus(transactionAmount)

        //when
        assertThrows<InvalidTransactionException> {
            walletService.updateBalance(walletModel, newBalance, transactionType)
        }

        //then
        verify(exactly = 0) {
            transactionService.createTransaction(any(), any())
        }

        assertEquals(oldBalance, walletModel.balance)
    }
}