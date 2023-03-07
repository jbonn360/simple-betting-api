package com.betting.simplebettingapi.model

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
class RollModel(var rollDt: Instant, @field:Min(0) @field:Max(10) var number: Byte = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1
}