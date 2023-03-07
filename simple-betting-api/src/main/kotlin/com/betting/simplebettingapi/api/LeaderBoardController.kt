package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.dto.LeaderBoardSizeDto
import com.betting.simplebettingapi.service.LeaderBoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/leaderboard")
class LeaderBoardController(@Autowired final val leaderBoardService: LeaderBoardService) {
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun handlePost(@RequestBody leaderBoardSize: LeaderBoardSizeDto): ResponseEntity<LeaderBoardDto> {
        return ResponseEntity<LeaderBoardDto>(leaderBoardService.getLeaderBoard(leaderBoardSize.size), HttpStatus.OK)
    }
}