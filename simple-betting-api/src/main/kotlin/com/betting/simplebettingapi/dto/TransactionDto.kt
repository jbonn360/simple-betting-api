package com.betting.simplebettingapi.dto

import java.math.BigDecimal
import java.time.Instant

class  TransactionDto(
    transactionDt: Instant,
    amount: BigDecimal,
    balanceBefore: BigDecimal = BigDecimal(-1),
    balanceAfter: BigDecimal = BigDecimal(-1))
{
    val transactionDt = transactionDt

    val amount = amount

    val balanceBefore = balanceBefore

    val balanceAfter = balanceAfter
}