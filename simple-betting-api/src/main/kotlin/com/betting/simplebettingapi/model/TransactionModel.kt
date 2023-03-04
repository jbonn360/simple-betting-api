package com.betting.simplebettingapi.model

import java.math.BigDecimal
import java.time.Instant
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class TransactionModel(
    transactionDt: Instant,
    amount: BigDecimal,
    balanceBefore: BigDecimal,
    balanceAfter: BigDecimal,
    wallet: WalletModel
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1

    val amount = amount

    val balanceBefore = balanceBefore

    val balanceAfter = balanceAfter

    @OneToOne
    val wallet = wallet

    val transactionDt = transactionDt
}