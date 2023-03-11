package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.AccountModel
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<AccountModel, Long> {
    fun findByUsername(username: String) : Optional<AccountModel>
}