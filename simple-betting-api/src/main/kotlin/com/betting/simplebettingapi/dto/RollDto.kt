package com.betting.simplebettingapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.Min

class RollDto(rollDt: Instant, numberRolled: Byte?) {
    val rollDt = rollDt

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var numberRolled = numberRolled
}