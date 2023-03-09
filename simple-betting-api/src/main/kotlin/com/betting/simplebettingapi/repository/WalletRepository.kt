package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.WalletModel
import org.springframework.data.repository.CrudRepository
import org.springframework.data.domain.Pageable;

interface WalletRepository : CrudRepository<WalletModel, Long> {
    fun findByAccountId(accountId: Long): WalletModel

    fun findAllByOrderByBalanceDesc(pageable: Pageable): List<WalletModel>
}