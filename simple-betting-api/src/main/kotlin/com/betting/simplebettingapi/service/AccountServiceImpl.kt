package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.exception.AccountCreationException
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Instant

import mu.KotlinLogging

@Service
class AccountServiceImpl(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val walletService: WalletService
) : AccountService {
    private val logger = KotlinLogging.logger {}

    override fun getAccountById(id: Int): AccountDto {
        // todo: handle not found case
        val accountModel = accountRepository.findById(id).get()

        return AccountDto(accountModel.id, accountModel.username, accountModel.name, accountModel.surname)
    }

    @Transactional
    override fun createAccount(accountDto: AccountDto): Int {
        // creating account and wallet
        val wallet = WalletModel(BigDecimal(0));
        val account = AccountModel(accountDto.username, accountDto.name, accountDto.surname, wallet)
        //wallet.account = account

        // saving account and wallet
        val accountSaved = accountRepository.save(account)

        // adding initial funds to wallet
        val walletUpdated = walletService.updateBalance(accountSaved.wallet, BigDecimal(1000))

        if (accountSaved != null && walletUpdated != null)
            return accountSaved.id
        else{
            logger.error { "Error occurred while creating account. Changes have been rolled back." }
            throw AccountCreationException("Failed to save account in database")
        }

    }
}