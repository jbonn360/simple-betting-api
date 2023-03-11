package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.WalletDto
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.WalletRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import java.math.BigDecimal
import java.util.*

class AccountServiceTests(
) {

    private val walletService: WalletService = mockk()

    private val accountRepository: AccountRepository = mockk()
    private val walletRepository: WalletRepository = mockk()

    private val startingCredits = BigDecimal(1000)

    private val accountService = AccountServiceImpl(
        accountRepository, walletRepository, walletService, startingCredits
    )

    @Test
    fun givenAccountId_WhenAccountExists_AccountIsReturned() {
        //given
        val walletModel = WalletModel(BigDecimal.ZERO)
        val accountModel = AccountModel("username", "name", "surname", walletModel)

        every { accountRepository.findById(any()) } returns Optional.of(accountModel)

        //when
        val accountDto = accountService.getAccountById(1)

        //then
        val walletDtoControl = WalletDto(walletModel.balance)
        val accountDtoControl = AccountDto(
            -1,
            accountModel.username,
            accountModel.name,
            accountModel.surname,
            walletDtoControl
        )

        assertEquals(accountDtoControl.username, accountDto.username)
        assertEquals(accountDtoControl.name, accountDto.name)
        assertEquals(accountDtoControl.surname, accountDto.surname)

        assertNotNull(accountDto.wallet)

        assertEquals(accountDtoControl.wallet!!.balance, accountDto.wallet!!.balance)
    }

    @Test
    fun givenAccountDto_WhenCreateAccountCalled_AccountIsCreated() {
        //given
        val walletDto = WalletDto(BigDecimal(Integer.MAX_VALUE))
        val accountDto = AccountDto(
            -1,
            "username",
            "name",
            "surname",
            walletDto
        )

        val walletModel = WalletModel(BigDecimal.ZERO)
        val accountModel = AccountModel(
            "username",
            "name",
            "surname",
            walletModel
        )

        every { walletRepository.save(any()) } returns walletModel
        every { accountRepository.save(any()) } returns accountModel
        every { walletService.updateBalance(any(), any(), any()) } returns mockk()
        every { accountRepository.findByUsername(any()) } returns Optional.empty()

        //when
        accountService.createAccount(accountDto)

        //then
        verify {
            accountRepository.save(withArg {
                assertEquals("username", it.username)
                assertEquals("name", it.name)
                assertEquals("surname", it.surname)
                assertEquals(BigDecimal.ZERO, it.wallet.balance)
            })
        }

        verify {
            walletRepository.save(withArg {
                assertEquals(walletModel, it)
            })
        }

        verify {
            walletService.updateBalance(withArg {
                assertEquals(walletModel, it)
                assertEquals(accountModel, it.account)
            }, withArg {
                assertEquals(startingCredits, it)
            }, withArg {
                assertEquals(TransactionType.INITIAL_DEPOSIT, it)
            })
        }
    }
}