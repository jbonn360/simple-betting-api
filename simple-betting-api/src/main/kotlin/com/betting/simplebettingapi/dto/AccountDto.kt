package com.betting.simplebettingapi.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class AccountDto(
    val id: Long = -1,

    @field:Size(min = 3, max = 10)
    val username: String,

    @field:Size(min = 3, max = 10)
    val name: String,

    @field:Size(min = 3, max = 10)
    val surname: String,

    val wallet: WalletDto?
)