package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.model.AccountModel
import com.betting.simplebettingapi.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {
    @Autowired
    private val accountRepository: AccountRepository? = null

    fun getAccountById(id: Int) : AccountDto{
        // todo: handle not found case
        val accountModel = accountRepository!!.findById(id).get();

        return AccountDto(accountModel.id, accountModel.username, accountModel.name, accountModel.surname);
    }

    fun createAccount(accountDto: AccountDto): Int {
        val accountModel = AccountModel(accountDto.username, accountDto.name, accountDto.surname, 1000 )
        val accountModelSaved = accountRepository?.save(accountModel)

        if (accountModelSaved != null)
            return accountModelSaved.id
        else
            throw Error("Failed to save account in database")
    }

}