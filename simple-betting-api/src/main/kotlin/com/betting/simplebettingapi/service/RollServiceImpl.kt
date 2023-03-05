package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.model.RollModel
import com.betting.simplebettingapi.repository.RollRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RollServiceImpl(@Autowired private val rollRepository: RollRepository) : RollService {
    override fun getNextRoll(): RollModel {
        return rollRepository.save(RollModel(
            Instant.now().plus(2, ChronoUnit.MINUTES),
            4
        ))
    }
}