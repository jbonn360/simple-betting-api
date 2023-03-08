package com.betting.simplebettingapi.helpers

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.RollDto
import com.betting.simplebettingapi.exception.NumberOutOfBoundsException
import com.betting.simplebettingapi.model.BetModel
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.random.Random

@Component
class Utils {
    fun generateRandomNumberRoll(): Byte {
        val random = Random.nextInt(1, 11)

        return random.toByte()
    }

    companion object {
        fun mapBetModelToDto(betModel: BetModel): BetDto {
            var rollResult: Byte? = null
            var amountWon: BigDecimal? = null

            if (betModel.status != BetStatus.PLACED) {
                rollResult = betModel.roll.number
                amountWon = calculatePrize(betModel.amount, betModel.status)
            }

            val roll = RollDto(
                betModel.roll.rollDt,
                rollResult
            )

            return BetDto(
                betModel.amount,
                betModel.numberBetOn,
                amountWon,
                betModel.status,
                betModel.placedDt,
                roll
            )
        }

        fun calculatePrize(betAmount: BigDecimal, betResult: BetStatus): BigDecimal {
            val result = when (betResult) {
                BetStatus.WON_10 -> betAmount.multiply(BigDecimal(10))
                BetStatus.WON_5 -> betAmount.multiply(BigDecimal(5))
                BetStatus.WON_HALF -> betAmount.multiply(BigDecimal(0.5))
                else -> BigDecimal(0)
            }

            return result
        }

        fun classifyBetResult(numberBetOn: Byte, rollResult: Byte): BetStatus {
            if (rollResult < 1 || rollResult > 10)
                throw NumberOutOfBoundsException("The rolled number '$rollResult' is out of bounds")

            if (numberBetOn < 1 || numberBetOn > 10)
                throw NumberOutOfBoundsException("The bet number '$numberBetOn' is out of bounds")

            val absDiff = abs(rollResult - numberBetOn)
            val result: BetStatus

            when (absDiff) {
                0 -> result = BetStatus.WON_10
                1 -> result = BetStatus.WON_5
                2 -> result = BetStatus.WON_HALF
                else -> result = BetStatus.LOST
            }

            return result
        }
    }
}