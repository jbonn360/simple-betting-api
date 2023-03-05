package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.model.RollModel
import java.math.BigDecimal
import java.time.Instant

class BetDto(
    betAmount: BigDecimal,
    numberBetOn: Byte,
    status: BetStatus = BetStatus.PLACED,
    placedDt: Instant = Instant.MIN,
    rollDt: Instant = Instant.MIN
) {
    val betAmount = betAmount
    val status = status
    val placedDt = placedDt
    val numberBetOn = numberBetOn
    val rollDt = rollDt
}