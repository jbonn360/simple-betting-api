package com.betting.simplebettingapi.dto

import java.math.BigDecimal

class LeaderBoardDto(leaders: List<LeaderBoardEntry>) {
    val leaders = leaders
    class LeaderBoardEntry(username: String, winnings: BigDecimal){
        val username = username
        val winnings = winnings
    }
}