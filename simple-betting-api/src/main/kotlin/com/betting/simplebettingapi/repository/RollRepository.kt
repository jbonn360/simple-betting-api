package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.RollModel
import org.springframework.data.repository.CrudRepository

interface RollRepository : CrudRepository<RollModel, Int> {
}