package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.model.RollModel
import java.math.BigDecimal
import java.time.Instant

class BetDto(
    betAmount: BigDecimal,
    status: BetStatus,
    placedDt: Instant,
    numberBetOn: Byte,
    rollDt: Instant?
) {
    val betAmount = betAmount
    val status = status
    val placedDt = placedDt
    val numberBetOn = numberBetOn
    val rollDt = rollDt
}