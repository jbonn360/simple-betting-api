package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.model.RollModel

interface RollService {
    fun getNextRoll(): RollModel
}