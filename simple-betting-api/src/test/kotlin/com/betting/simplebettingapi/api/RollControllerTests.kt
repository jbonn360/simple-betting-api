package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.RollDto
import com.betting.simplebettingapi.service.BetService
import com.betting.simplebettingapi.service.RollService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.Instant

@WebMvcTest(controllers = [RollController::class])
class RollControllerTests (
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val mapper: ObjectMapper
) {
    @MockkBean
    private lateinit var rollService: RollService

    @Test
    fun givenRequestForCurrentRoll_WhenRequestValid_ThenReturnCurrentRoll() {
        //given
        val rollDto = RollDto(
            Instant.now(),
            null
        )

        every {
            rollService.getCurrentRollDto()
        } returns rollDto

        //when / then
        mockMvc.get("/api/v1/roll") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(mapper.writeValueAsString(rollDto)) }
        }
    }
}