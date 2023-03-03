package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/account")
class AccountController {
    @Autowired
    private val accountService: AccountService? = null

    @GetMapping("/{id}")
    fun handleGet (id: Int): ResponseEntity<AccountDto>{
        return ResponseEntity<AccountDto>(accountService!!.getAccountById(id), HttpStatus.OK)
    }

    @PostMapping
    fun handlePost (@RequestBody accountDto: AccountDto): ResponseEntity<Any?> {
        val accountId: Int = accountService!!.createAccount(accountDto)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "")

        return ResponseEntity<Any?>(null, HttpStatus.CREATED)
    }
}