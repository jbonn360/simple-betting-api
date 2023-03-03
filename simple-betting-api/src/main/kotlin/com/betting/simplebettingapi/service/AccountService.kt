package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface AccountService {
    fun getAccountById(id: Int) : AccountDto

    fun createAccount(accountDto: AccountDto): Int

}