package com.betting.simplebettingapi.repository

import com.betting.simplebettingapi.model.AccountModel
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<AccountModel, Int> {
}