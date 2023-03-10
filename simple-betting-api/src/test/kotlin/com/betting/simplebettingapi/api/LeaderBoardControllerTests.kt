package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.LeaderBoardDto
import com.betting.simplebettingapi.service.LeaderBoardService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import reactor.core.publisher.Mono
import java.math.BigDecimal

@WebMvcTest(controllers = [LeaderBoardController::class])
class LeaderBoardControllerTests (
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val mapper: ObjectMapper
) {
    @MockkBean
    private lateinit var leaderBoardService: LeaderBoardService

    @Test
    fun givenGetRequest_WhenRequestIsValid_ThenReturnLeaderBoard(){
        //given
        val entry1 = LeaderBoardDto.LeaderBoardEntry(
            "user1",
            BigDecimal(10000)
        )

        val entry2 = LeaderBoardDto.LeaderBoardEntry(
            "user2",
            BigDecimal(5000)
        )

        val entry3 = LeaderBoardDto.LeaderBoardEntry(
            "user2",
            BigDecimal(1000)
        )

        val leaderBoardDto = LeaderBoardDto(listOf(entry1, entry2, entry3))

        every{ leaderBoardService.getLeaderBoard() } returns
                Mono.just(leaderBoardDto)

        //when / then
        mockMvc.get("/api/v1/leaderboard") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(leaderBoardDto)) }
        }
    }
}