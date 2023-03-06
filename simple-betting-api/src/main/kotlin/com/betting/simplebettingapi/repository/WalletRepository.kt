package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.WalletModel
import org.springframework.data.repository.CrudRepository
import org.springframework.data.domain.Pageable;

interface WalletRepository : CrudRepository<WalletModel, Int> {
    fun findByAccountId(accountId: Int): WalletModel

    fun findAllByOrderByBalanceDesc(pageable: Pageable): List<WalletModel>
}