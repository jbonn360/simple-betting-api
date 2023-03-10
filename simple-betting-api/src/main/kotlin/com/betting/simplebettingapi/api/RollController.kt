package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.RollDto
import com.betting.simplebettingapi.service.RollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/roll")
class RollController(
    @Autowired private val rollService: RollService
) {
    @GetMapping(produces = ["application/json"])
    fun getCurrentRoll(): RollDto {
        return rollService.getCurrentRollDto()
    }
}