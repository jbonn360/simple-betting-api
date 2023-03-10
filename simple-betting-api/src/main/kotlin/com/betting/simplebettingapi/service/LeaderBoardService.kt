package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.LeaderBoardDto
import reactor.core.publisher.Mono

interface LeaderBoardService {
    fun getLeaderBoard(): Mono<LeaderBoardDto>
}