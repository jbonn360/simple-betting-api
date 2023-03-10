package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.exception.InvalidTransactionException
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.WalletRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Instant

//import org.slf4j.LoggerFactory
//import java.util.logging.Logger
import mu.KotlinLogging

@Service
class WalletServiceImpl(
    @Autowired private val transactionService: TransactionService,
    @Autowired private val walletRepository: WalletRepository
) : WalletService {
    //private val logger = LoggerFactory.getLogger(WalletServiceImpl::class.simpleName)
    private val logger = KotlinLogging.logger {}

    /*
     * Updates the specified wallet's balance and creates a transaction record in the database
     */
    @Throws(InsufficientCreditsException::class, InvalidTransactionException::class)
    @Transactional
    override fun updateBalance(
        wallet: WalletModel,
        newBalance: BigDecimal,
        transactionType: TransactionType
    ): TransactionModel {
        val transactionAmount = newBalance - wallet.balance

        if (newBalance < BigDecimal.ZERO) // if new balance is less than zero
            throw InsufficientCreditsException(
                "Cannot set wallet balance to $newBalance. " +
                        "Transaction has been rolled back."
            )
        else if (newBalance == wallet.balance) // if transaction amount is zero
            throw InvalidTransactionException("Cannot have a transaction with amount set to 0")
        else {
            // update credits in wallet
            wallet.balance = newBalance
        }

        // saving the transaction
        return transactionService.createTransaction(
            TransactionDto(Instant.now(), transactionType, transactionAmount), wallet
        );
    }

    override fun getWalletByAccountId(accountId: Long): WalletModel {
        return walletRepository.findByAccountId(accountId).orElseThrow{
            EntityNotFoundException("Account with id $accountId was not found")
        }
    }

}