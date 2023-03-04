package com.betting.simplebettingapi.helpers

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.model.BetModel

class Utils {
    companion object {
        fun mapBetModelToDto(betModel: BetModel): BetDto{
            return BetDto(
                betModel.amount,
                betModel.status,
                betModel.placedDt,
                betModel.numberBet,
                betModel.roll.rollDt
            )
        }
    }
}