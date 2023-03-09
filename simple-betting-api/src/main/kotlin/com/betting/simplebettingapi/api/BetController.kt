package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto
import com.betting.simplebettingapi.service.BetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class BetController(@Autowired private val betService: BetService) {
    @GetMapping("/account/{accountId}/bets", produces = ["application/json"])
    fun handleGetWithAccountId(@PathVariable("accountId") accountId: Int): ResponseEntity<BetListDto> {
        return ResponseEntity<BetListDto>(betService.getBetsByAccountId(accountId), HttpStatus.OK)
    }

    @PostMapping("/account/{accountId}/bets", consumes = ["application/json"], produces = ["application/json"])
    fun handlePost(@PathVariable("accountId") accountId: Int, @RequestBody betDto: BetDto):
            ResponseEntity<HttpHeaders> {
        val betId = betService.placeBet(accountId, betDto)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "/api/v1/bets/$betId")

        return ResponseEntity<HttpHeaders>(httpHeaders, HttpStatus.CREATED)
    }
}