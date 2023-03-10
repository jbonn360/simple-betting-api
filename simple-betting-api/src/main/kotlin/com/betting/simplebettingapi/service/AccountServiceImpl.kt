package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.WalletDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
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
import org.springframework.beans.factory.annotation.Value

@Service
class AccountServiceImpl(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val walletRepository: WalletRepository,
    @Autowired private val walletService: WalletService,
    @Value("\${app.wallet.initial-credits}") private val initialCredits: BigDecimal
) : AccountService {
    private val logger = KotlinLogging.logger {}

    override fun getAccountById(id: Long): AccountDto {
        val accountModel = accountRepository.findById(id).orElseThrow{
            EntityNotFoundException("Account with id $id was not found")
        }

        return AccountDto(
            accountModel.id,
            accountModel.username,
            accountModel.name,
            accountModel.surname,
            WalletDto(accountModel.wallet.balance)
        )
    }

    @Transactional
    override fun createAccount(accountDto: AccountDto): Long {
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
        walletService.updateBalance(walletSaved, initialCredits, TransactionType.INITIAL_DEPOSIT)

        return accountSaved.id
    }
}