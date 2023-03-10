package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.BetDto
import com.betting.simplebettingapi.dto.BetListDto
import com.betting.simplebettingapi.dto.RollDto
import com.betting.simplebettingapi.helpers.BetStatus
import com.betting.simplebettingapi.service.BetService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.time.Instant
import java.time.temporal.ChronoUnit

@WebMvcTest(controllers = [BetController::class])
class BetControllerTests(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val mapper: ObjectMapper
) {
    @MockkBean
    private lateinit var betService: BetService

    private val rollDto = RollDto(Instant.now().plus(1, ChronoUnit.MINUTES), null)

    private val betDto1 = BetDto(
        BigDecimal(100),
        5,
        null,
        BetStatus.PLACED,
        Instant.now(),
        rollDto
    )

    private val betDto2 = BetDto(
        BigDecimal(200),
        1,
        null,
        BetStatus.PLACED,
        Instant.now(),
        rollDto
    )

    @Test
    fun givenBetId_WhenBetExists_ThenReturnBet() {
        //given
        val accountId: Long = 1
        val betId: Long = 1

        every {
            betService.getBetByAccountIdAndBetId(accountId, betId)
        } returns betDto2

        val betDtoStr = mapper.writeValueAsString(betDto2)

        //when / then
        mockMvc.get("/api/v1/account/$accountId/bets/$betId") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(betDtoStr) }
        }
    }

    @Test
    fun givenAccountId_WhenAccountExists_ThenReturnListOfBets() {
        //given
        val accountId: Long = 1
        val betListDto = BetListDto(listOf(betDto1, betDto2))

        every {
            betService.getBetsByAccountId(accountId)
        } returns betListDto

        val betListDtoStr = mapper.writeValueAsString(betListDto)

        //when / then
        mockMvc.get("/api/v1/account/$accountId/bets") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(betListDtoStr) }
        }
    }

    @Test
    fun givenBetDtoAndAccountId_whenAccountExists_thenSaveBet() {
        //given
        val accountId: Long = 1
        val betId: Long = 1

        every { betService.placeBet(accountId, any()) } returns betId

        //when / then
        mockMvc.post("/api/v1/account/$accountId/bets") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(betDto1)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            header { string("Location", "/api/v1/account/$accountId/bets/$betId") }
        }
    }
}