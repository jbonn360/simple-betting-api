package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.BetModel
import org.springframework.data.repository.CrudRepository

interface BetRepository : CrudRepository<BetModel, Int>{
    fun findAllByAccount_IdOrderByPlacedDtDesc(accountId: Int): List<BetModel>
}