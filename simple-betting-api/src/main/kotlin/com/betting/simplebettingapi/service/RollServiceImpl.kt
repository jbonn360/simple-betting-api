package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.repository.RollRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RollServiceImpl(@Autowired private val rollRepository: RollRepository) : RollService {
    override fun getNextRoll(): RollModel {
        TODO("Not yet implemented")
    }
}