package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.RollDto
import com.betting.simplebettingapi.model.RollModel

interface RollService {
    fun getCurrentRoll(): RollModel
    fun getCurrentRollDto(): RollDto
}