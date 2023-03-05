package com.betting.simplebettingapi.helpers

enum class BetStatus {
    PLACED, WON_10, WON_5, WON_HALF, LOST;

    companion object {
        fun isWinningStatus(status: BetStatus): Boolean {
            return (status == BetStatus.WON_10 || status == BetStatus.WON_5 || status == BetStatus.WON_HALF)
        }
    }
}