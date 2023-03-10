package com.betting.simplebettingapi.model

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
class AccountModel(
    @Column(unique = true)
    val username: String,

    val name: String,

    val surname: String,

    @OneToOne
    val wallet: WalletModel
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1;
}