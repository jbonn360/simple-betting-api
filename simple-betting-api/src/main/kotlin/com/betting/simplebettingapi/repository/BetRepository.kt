package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.BetModel
import com.betting.simplebettingapi.model.RollModel
import org.springframework.data.repository.CrudRepository

interface BetRepository : CrudRepository<BetModel, Long>{
    fun findAllByAccountIdOrderByPlacedDtDesc(accountId: Long): List<BetModel>

    fun findAllByRoll(roll: RollModel): List<BetModel>
}