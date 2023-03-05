package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.model.RollModel
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.math.BigDecimal
import java.time.Instant


class BetDto(
    betAmount: BigDecimal,
    numberBetOn: Byte,
    amountWon: BigDecimal?,
    status: BetStatus = BetStatus.PLACED,
    placedDt: Instant = Instant.MIN,
    roll: RollDto?,
) {
    val betAmount = betAmount
    val numberBetOn = numberBetOn

    @JsonInclude(Include.NON_NULL)
    val amountWon = amountWon

    val status = status
    val placedDt = placedDt
    val roll = roll
}