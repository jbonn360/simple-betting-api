package com.betting.simplebettingapi.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.PositiveOrZero

@Entity
class WalletModel (balance: BigDecimal) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1;

    @PositiveOrZero
    var balance = balance

    @OneToOne
    var account: AccountModel? = null
}