package com.betting.simplebettingapi.service

import com.betting.simplebettingapi.dto.LeaderBoardDto
import org.springframework.data.repository.CrudRepository

interface LeaderBoardService {
    fun getLeaderBoard(listSize: Int): LeaderBoardDto
}