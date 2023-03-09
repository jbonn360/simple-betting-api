package com.betting.simplebettingapi.model

import com.betting.simplebettingapi.helpers.TransactionType
import java.math.BigDecimal
import java.time.Instant
import javax.persistence.*

@Entity
class TransactionModel(
    val transactionDt: Instant,

    @Enumerated(EnumType.ORDINAL)
    val transactionType: TransactionType,

    val amount: BigDecimal,

    val balanceBefore: BigDecimal,

    val balanceAfter: BigDecimal,

    @OneToOne
    val wallet: WalletModel
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}