package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import java.math.BigDecimal

interface WalletService {
    fun updateBalance(wallet: WalletModel, newBalance: BigDecimal): TransactionModel

    fun getWalletByAccountId(accountId: Int): WalletModel
}