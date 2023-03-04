package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.TransactionModel
import org.springframework.data.repository.CrudRepository

interface TransactionRepository : CrudRepository<TransactionModel, Int> {
    fun findAllByWallet_Id(walletId: Int): List<TransactionModel>
}