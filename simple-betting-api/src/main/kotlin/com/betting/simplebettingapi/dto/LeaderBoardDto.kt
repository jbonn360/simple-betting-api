package com.betting.simplebettingapi.dto

import java.math.BigDecimal

class LeaderBoardDto(val leaders: List<LeaderBoardEntry>) {
    class LeaderBoardEntry(username: String, winnings: BigDecimal){
        val username = username
        val winnings = winnings
    }
}