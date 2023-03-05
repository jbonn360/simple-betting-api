package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.WalletModel
import org.springframework.data.repository.CrudRepository

interface WalletRepository : CrudRepository<WalletModel, Int> {
    fun findByAccountId(accountId: Int): WalletModel
}