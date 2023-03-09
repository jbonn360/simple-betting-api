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
import reactor.core.publisher.Mono
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/account")
class AccountController(@Autowired private val accountService: AccountService) {
    @GetMapping("/{id}", produces = ["application/json"])
    fun getAccountById (@PathVariable("id") id: Long): AccountDto {
        return accountService.getAccountById(id)
    }

    @PostMapping(consumes = ["application/json"])
    fun createNewAccount (@Valid @RequestBody accountDto: AccountDto): ResponseEntity<HttpHeaders> {
        val accountId: Long = accountService.createAccount(accountDto)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "/api/v1/account/${accountId}")

        return ResponseEntity<HttpHeaders>(httpHeaders, HttpStatus.CREATED)
    }
}