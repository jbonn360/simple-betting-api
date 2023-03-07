package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.WalletDto
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.WalletRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

import mu.KotlinLogging

@Service
class AccountServiceImpl(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val walletRepository: WalletRepository,
    @Autowired private val walletService: WalletService
) : AccountService {
    private val logger = KotlinLogging.logger {}

    override fun getAccountById(id: Int): AccountDto {
        // todo: handle not found case
        val accountModel = accountRepository.findById(id).get()

        return AccountDto(
            accountModel.id,
            accountModel.username,
            accountModel.name,
            accountModel.surname,
            WalletDto(accountModel.wallet.balance)
        )
    }

    @Transactional
    override fun createAccount(accountDto: AccountDto): Int {
        // creating and saving wallet
        val wallet = WalletModel(BigDecimal(0))
        val walletSaved = walletRepository.save(wallet)

        // creating and saving account
        val account = AccountModel(accountDto.username, accountDto.name, accountDto.surname, walletSaved)
        val accountSaved = accountRepository.save(account)

        // linking the wallet back to the account to get a bidirectional relationship for easier querying
        walletSaved.account = accountSaved
        walletRepository.save(walletSaved)

        // adding initial funds to wallet
        walletService.updateBalance(walletSaved, BigDecimal(1000), TransactionType.INITIAL_DEPOSIT)

        return accountSaved.id
    }
}