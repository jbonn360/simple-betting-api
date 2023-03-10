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
    @field:Positive
    val amount: BigDecimal,

    @field:Min(1)
    @field:Max(10)
    val numberBetOn: Byte,

    @Enumerated(EnumType.ORDINAL)
    var status: BetStatus,

    val placedDt: Instant,

    @OneToOne
    val roll: RollModel,

    @OneToOne
    val account: AccountModel
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}