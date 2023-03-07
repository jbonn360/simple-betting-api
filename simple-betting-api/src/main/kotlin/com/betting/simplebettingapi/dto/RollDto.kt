package com.betting.simplebettingapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.Min

class RollDto(
    val rollDt: Instant,
    @field:Min(1) @field:Max(10) @field:JsonInclude(JsonInclude.Include.NON_NULL) var numberRolled: Byte?
)