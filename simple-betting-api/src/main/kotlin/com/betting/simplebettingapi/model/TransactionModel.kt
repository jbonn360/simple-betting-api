package com.betting.simplebettingapi.model

import com.betting.simplebettingapi.helpers.TransactionType
import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*

@Entity
class TransactionModel(
    transactionDt: Instant,
    transactionType: TransactionType,
    amount: BigDecimal,
    balanceBefore: BigDecimal,
    balanceAfter: BigDecimal,
    wallet: WalletModel
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = -1

    val amount = amount

    @Enumerated(EnumType.ORDINAL)
    val transactionType = transactionType

    val balanceBefore = balanceBefore

    val balanceAfter = balanceAfter

    @OneToOne
    val wallet = wallet

    val transactionDt = transactionDt
}