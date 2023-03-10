package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.TransactionListDto
import com.betting.simplebettingapi.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TransactionController(@Autowired private val transactionService: TransactionService) {
    @GetMapping("/account/{accountId}/transactions", produces = ["application/json"])
    fun getTransactionsByAccountId(@PathVariable("accountId") accountId: Long):
            ResponseEntity<TransactionListDto>
    {
        return ResponseEntity<TransactionListDto>(
            transactionService.getTransactionsByAccountId(accountId), HttpStatus.OK
        )
    }
}