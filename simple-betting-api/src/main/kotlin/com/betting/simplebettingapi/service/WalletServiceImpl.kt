package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.exception.InvalidTransactionException
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
    override fun updateBalance(wallet: WalletModel, newBalance: BigDecimal): TransactionModel {
        val transactionAmount = newBalance - wallet.balance
        val walletUpdated: WalletModel

        if (newBalance < BigDecimal.ZERO) // if new balance is less than zero
            throw InsufficientCreditsException("Cannot set wallet balance to $newBalance")
        else if (newBalance == wallet.balance) // if transaction amount is zero
            throw InvalidTransactionException("Cannot have a transaction with amount set to 0")
        else {
            // update credits in wallet
            wallet.balance = newBalance
            walletUpdated = walletRepository.save(wallet)
        }

        if(walletUpdated == null){
            logger.error { "Error occurred while updating the wallet's balance" }
            throw Exception("Failed to update wallet in database")
        }

        // saving the transaction
        val transaction = transactionService.createTransaction(
            TransactionDto(Instant.now(), transactionAmount), wallet
        )

        if(transaction == null){
            logger.error { "Error occurred while persisting the transaction record in the database" }
            throw Exception("Failed to persist transaction record in database")
        }

        return transaction;
    }

    override fun getWalletByAccountId(accountId: Int): WalletModel {
        return walletRepository.findByAccountId(accountId)
    }

}