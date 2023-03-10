package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import java.math.BigDecimal

interface WalletService {
    fun updateBalance(wallet: WalletModel, newBalance: BigDecimal, transactionType: TransactionType): TransactionModel

    fun getWalletByAccountId(accountId: Long): WalletModel
}