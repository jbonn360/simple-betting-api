package com.betting.simplebettingapi.helpers

import com.betting.simplebettingapi.exception.NumberOutOfBoundsException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UtilsTests {
    @Test
    fun givenCallToGenerateRandomNumber_ThenRandomNumberBetweenOneAndTenGenerated(){
        for(i in 1..1_000_000){
            val random = Utils().generateRandomNumberRoll()
            assertTrue(random in 1..10)
        }
    }

    @Test
    fun givenNumberBetOnMatchesBetResult_whenClassifyBetResultCalled_ThenWon10Returned(){
        val numberBetOn: Byte = 5
        val betResult: Byte = 5

        val status = Utils.classifyBetResult(numberBetOn, betResult)

        assertEquals(BetStatus.WON_10, status)
    }

    @Test
    fun givenNumberBetOnOneOffFromBetResult_whenClassifyBetResultCalled_ThenWon5Returned(){
        val numberBetOn: Byte = 4
        val betResult: Byte = 5

        val status = Utils.classifyBetResult(numberBetOn, betResult)

        assertEquals(BetStatus.WON_5, status)
    }

    @Test
    fun givenNumberBetOnTwoOffFromBetResult_whenClassifyBetResultCalled_ThenWonHalfReturned(){
        val numberBetOn: Byte = 5
        val betResult: Byte = 3

        val status = Utils.classifyBetResult(numberBetOn, betResult)

        assertEquals(BetStatus.WON_HALF, status)
    }

    @Test
    fun givenNumberBetOnThreeOffFromBetResult_whenClassifyBetResultCalled_ThenLostReturned(){
        val numberBetOn: Byte = 5
        val betResult: Byte = 2

        val status = Utils.classifyBetResult(numberBetOn, betResult)

        assertEquals(BetStatus.LOST, status)
    }

    @Test
    fun givenNumberBetOnOutOfBounds_whenClassifyBetResultCalled_ThenErrorThrown(){
        val numberBetOn: Byte = 0
        val betResult: Byte = 5

        assertThrows<NumberOutOfBoundsException> {
            Utils.classifyBetResult(numberBetOn, betResult)
        }
    }
}