package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto

interface BetService {
    fun getBetsByAccountId(accountId: Int): BetListDto
    fun getBetById(betId: Int): BetDto
    fun placeBet(accountId: Int, betDto: BetDto): Int
}