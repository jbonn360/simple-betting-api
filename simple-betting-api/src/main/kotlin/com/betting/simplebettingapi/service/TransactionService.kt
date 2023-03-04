package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.dto.TransactionListDto
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel

interface TransactionService {
    fun createTransaction(transactionDto: TransactionDto, wallet: WalletModel): TransactionModel
    fun getTransactionsByAccountId(accountId: Int): TransactionListDto
}