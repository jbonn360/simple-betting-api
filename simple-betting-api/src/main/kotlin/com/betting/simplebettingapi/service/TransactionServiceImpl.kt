package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.dto.TransactionListDto
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import com.betting.simplebettingapi.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val walletService: WalletService
) : TransactionService {

    override fun createTransaction(transactionDto: TransactionDto, wallet: WalletModel): TransactionModel {
        val transaction = TransactionModel(
            transactionDto.transactionDt,
            transactionDto.amount,
            wallet.balance,
            wallet.balance.add(transactionDto.amount),
            wallet
        )

        val transactionSaved = transactionRepository.save(transaction);

        return transactionSaved;
    }

    override fun getTransactionsByAccountId(accountId: Int): TransactionListDto {
        val transactionModels = transactionRepository.findAllByWallet_Id(
            walletService.getWalletByAccountId(accountId).id
        );

        val transactions = ArrayList<TransactionDto>()

        transactionModels.forEach{
            tm -> transactions.add(TransactionDto(tm.transactionDt, tm.amount, tm.balanceBefore, tm.balanceAfter))
        }

        return TransactionListDto(transactions)
    }
}