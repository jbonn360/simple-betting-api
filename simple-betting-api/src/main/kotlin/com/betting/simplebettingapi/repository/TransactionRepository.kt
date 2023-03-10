package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.model.WalletModel
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<TransactionModel, Long> {
    fun findAllByWalletOrderByTransactionDtDesc(wallet: WalletModel): List<TransactionModel>
}