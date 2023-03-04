package com.betting.simplebettingapi.model

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
class RollModel(rollDt: Instant, number: Byte) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1

    @Min(1)
    @Max(10)
    val number = number
}