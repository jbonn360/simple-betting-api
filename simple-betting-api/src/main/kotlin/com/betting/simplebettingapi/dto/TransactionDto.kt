package com.betting.simplebettingapi.dto

import com.betting.simplebettingapi.helpers.TransactionType
import java.math.BigDecimal
import java.time.Instant

class  TransactionDto(
    transactionDt: Instant,
    transactionType: TransactionType,
    amount: BigDecimal,
    balanceBefore: BigDecimal = BigDecimal(-1),
    balanceAfter: BigDecimal = BigDecimal(-1))
{
    val transactionDt = transactionDt

    val transactionType = transactionType

    val amount = amount

    val balanceBefore = balanceBefore

    val balanceAfter = balanceAfter
}