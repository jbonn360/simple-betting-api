package com.betting.simplebettingapi.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.PositiveOrZero

@Entity
class WalletModel(
    @field:PositiveOrZero
    var balance: BigDecimal,

    @OneToOne
    var account: AccountModel? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1;
}