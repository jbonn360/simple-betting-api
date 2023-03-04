package com.betting.simplebettingapi.dto

import java.math.BigDecimal
import java.time.Instant

class  TransactionDto(
    transactionDt: Instant,
    amount: BigDecimal,
    balanceBefore: BigDecimal,
    balanceAfter: BigDecimal)
{
    val transactionDt = transactionDt

    val amount = amount

    val balanceBefore = balanceBefore

    val balanceAfter = balanceAfter

    constructor(transactionDt: Instant, amount: BigDecimal) :
            this(transactionDt, amount, BigDecimal(-1), BigDecimal(-1))

}