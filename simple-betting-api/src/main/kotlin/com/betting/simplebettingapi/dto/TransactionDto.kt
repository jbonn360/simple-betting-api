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

    constructor(transactionDt: Instant, amount: BigDecimal) :
            this(transactionDt, amount, BigDecimal(-1), BigDecimal(-1))

}