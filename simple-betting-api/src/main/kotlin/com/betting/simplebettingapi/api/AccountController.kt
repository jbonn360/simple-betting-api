package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/account")
class AccountController(@Autowired private val accountService: AccountService) {
    @GetMapping("/{id}")
    fun handleGet (@PathVariable("id") id: Int): ResponseEntity<AccountDto>{
        return ResponseEntity<AccountDto>(accountService.getAccountById(id), HttpStatus.OK)
    }

    @PostMapping
    fun handlePost (@Valid @RequestBody accountDto: AccountDto): ResponseEntity<HttpHeaders> {
        val accountId: Int = accountService.createAccount(accountDto)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "/api/v1/account/${accountId}")

        return ResponseEntity<HttpHeaders>(httpHeaders, HttpStatus.CREATED)
    }
}