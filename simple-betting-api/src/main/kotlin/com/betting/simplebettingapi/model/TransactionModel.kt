package com.betting.simplebettingapi.model

import java.time.Instant
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class TransactionModel (transactionDt: Instant, amount: Int, balanceBefore: Int, balanceAfter: Int, account: AccountModel) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1
}