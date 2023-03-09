package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.TransactionRepository
import com.betting.simplebettingapi.repository.WalletRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

class TransactionServiceTests {
    // mock dependencies
    private val transactionRepository: TransactionRepository = mockk()
    private val walletRepository: WalletRepository = mockk()

    // create roll service impl instance - class to be tested
    private val transactionService = TransactionServiceImpl(
        transactionRepository, walletRepository
    )

    @Test
    fun givenTransactionDtoAndWalletModel_WhenCreatedTransactionIsCalled_TransactionIsCreated() {
        //given
        val accountModel: AccountModel = mockk()

        val updatedWallet = WalletModel(
            BigDecimal(250),
            accountModel
        )

        val transactionAmount = BigDecimal(100)

        val transactionDto = TransactionDto(
            Instant.now(),
            TransactionType.INITIAL_DEPOSIT,
            transactionAmount,
            updatedWallet.balance.minus(transactionAmount),
            updatedWallet.balance
        )

        every{ transactionRepository.save(any()) } returns mockk()

        //when
        transactionService.createTransaction(transactionDto, updatedWallet)

        //then
        verify{ transactionRepository.save(withArg {
            assertEquals(transactionDto.transactionDt , it.transactionDt)
            assertEquals(transactionDto.transactionType , it.transactionType)
            assertEquals(transactionDto.amount , it.amount)
            assertEquals(transactionDto.balanceBefore , it.balanceBefore)
            assertEquals(transactionDto.balanceAfter , it.balanceAfter)
            assertEquals(updatedWallet , it.wallet)
        }) }
    }

    @Test
    fun givenAccountId_WhenAccountExists_ThenGetTransactionsForAccount(){
        //given
        val walletModel = WalletModel(
            BigDecimal(500),
            mockk()
        )

        val transactionModel1 = TransactionModel(
            Instant.now(),
            TransactionType.INITIAL_DEPOSIT,
            BigDecimal(111),
            BigDecimal(50),
            BigDecimal(161),
            walletModel
        )

        val transactionModel2 = TransactionModel(
            Instant.now(),
            TransactionType.BET_PLACEMENT,
            BigDecimal(-50),
            BigDecimal(300),
            BigDecimal(250),
            walletModel
        )

        every{ walletRepository.findByAccountId(1) } returns walletModel
        every {
            transactionRepository.findAllByWalletOrderByTransactionDtDesc(walletModel)
        } returns listOf(transactionModel1, transactionModel2)

        //when
        val transactionsDto = transactionService.getTransactionsByAccountId(1)

        //then
        assertEquals(2, transactionsDto.transactions.size)

        val transaction1 = transactionsDto.transactions[0]
        assertEquals(transactionModel1.transactionDt, transaction1.transactionDt)
        assertEquals(transactionModel1.transactionType, transaction1.transactionType)
        assertEquals(transactionModel1.amount, transaction1.amount)
        assertEquals(transactionModel1.balanceBefore, transaction1.balanceBefore)
        assertEquals(transactionModel1.balanceAfter, transaction1.balanceAfter)

        val transaction2 = transactionsDto.transactions[1]
        assertEquals(transactionModel2.transactionDt, transaction2.transactionDt)
        assertEquals(transactionModel2.transactionType, transaction2.transactionType)
        assertEquals(transactionModel2.amount, transaction2.amount)
        assertEquals(transactionModel2.balanceBefore, transaction2.balanceBefore)
        assertEquals(transactionModel2.balanceAfter, transaction2.balanceAfter)

    }
}