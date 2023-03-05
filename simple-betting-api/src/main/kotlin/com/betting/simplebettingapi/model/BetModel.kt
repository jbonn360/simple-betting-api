package com.betting.simplebettingapi.model

import com.betting.simplebettingapi.helpers.BetStatus
import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

import javax.validation.constraints.Positive


@Entity
class BetModel(
    amount: BigDecimal,
    numberBetOn: Byte,
    status: BetStatus,
    placedDt: Instant,
    roll: RollModel,
    account: AccountModel
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1;

    @Positive
    val amount = amount

    val placedDt = placedDt

    @Min(1)
    @Max(10)
    val numberBetOn = numberBetOn

    @Enumerated(EnumType.ORDINAL)
    var status = status

    @OneToOne
    val roll = roll

    @OneToOne
    val account = account
}