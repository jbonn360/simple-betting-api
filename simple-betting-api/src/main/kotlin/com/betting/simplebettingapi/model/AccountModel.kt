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
class AccountModel(username: String, name: String, surname: String, wallet: WalletModel) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1;

    @Column(unique = true)
    val username = username

    val name = name

    val surname = surname

    @OneToOne
    val wallet = wallet
}