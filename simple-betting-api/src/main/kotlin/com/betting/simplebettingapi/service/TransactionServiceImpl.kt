package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.dto.TransactionListDto
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.TransactionRepository
import com.betting.simplebettingapi.repository.WalletRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val walletRepository: WalletRepository
) : TransactionService {

    override fun createTransaction(transactionDto: TransactionDto, updatedWallet: WalletModel): TransactionModel {
        val transaction = TransactionModel(
            transactionDto.transactionDt,
            transactionDto.transactionType,
            transactionDto.amount,
            updatedWallet.balance.subtract(transactionDto.amount),
            updatedWallet.balance,
            updatedWallet
        )

        val transactionSaved = transactionRepository.save(transaction);

        return transactionSaved;
    }

    override fun getTransactionsByAccountId(accountId: Long): TransactionListDto {
        val wallet = walletRepository.findByAccountId(accountId)
        val transactionModels = transactionRepository.findAllByWalletOrderByTransactionDtDesc(wallet)
        val transactions = ArrayList<TransactionDto>()

        transactionModels.forEach{
            tm -> transactions.add(TransactionDto(tm.transactionDt, tm.transactionType, tm.amount, tm.balanceBefore, tm.balanceAfter))
        }

        return TransactionListDto(transactions)
    }
}