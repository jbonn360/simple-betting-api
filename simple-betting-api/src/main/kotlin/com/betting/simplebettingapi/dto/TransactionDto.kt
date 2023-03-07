package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.TransactionType
import java.math.BigDecimal
import java.time.Instant

class  TransactionDto(
    val transactionDt: Instant,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val balanceBefore: BigDecimal = BigDecimal(-1),
    val balanceAfter: BigDecimal = BigDecimal(-1)
)
