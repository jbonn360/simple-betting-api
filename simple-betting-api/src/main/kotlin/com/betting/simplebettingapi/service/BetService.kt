package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto

interface BetService {
    fun getBetsByAccountId(accountId: Long): BetListDto
    fun getBetByBetId(betId: Long): BetDto
    fun placeBet(accountId: Long, betDto: BetDto): Long
}