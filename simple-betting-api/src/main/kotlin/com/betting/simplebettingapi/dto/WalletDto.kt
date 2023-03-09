package com.betting.simplebettingapi.dto

import java.math.BigDecimal
import javax.validation.constraints.PositiveOrZero

class WalletDto(
    @field:PositiveOrZero
    val balance: BigDecimal
)