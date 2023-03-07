package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.model.RollModel
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.math.BigDecimal
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero


class BetDto(
    @field:Positive val betAmount: BigDecimal,
    @field:Min(1) @field:Max(10) val numberBetOn: Byte,
    @field:PositiveOrZero @field:JsonInclude(Include.NON_NULL) val amountWon: BigDecimal?,
    val status: BetStatus = BetStatus.PLACED,
    val placedDt: Instant = Instant.MIN,
    val roll: RollDto?,
)