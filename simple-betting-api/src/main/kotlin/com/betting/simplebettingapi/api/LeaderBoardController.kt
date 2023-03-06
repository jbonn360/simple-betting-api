package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.dto.LeaderBoardSizeDto
import com.betting.simplebettingapi.service.LeaderBoardService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/leaderboard")
class LeaderBoardController(@Autowired final val leaderBoardService: LeaderBoardService) {
    @GetMapping
    fun handleGet(@RequestBody leaderBoardSize: LeaderBoardSizeDto): ResponseEntity<LeaderBoardDto> {
        return ResponseEntity<LeaderBoardDto>(leaderBoardService.getLeaderBoard(leaderBoardSize.size), HttpStatus.OK)
    }
}