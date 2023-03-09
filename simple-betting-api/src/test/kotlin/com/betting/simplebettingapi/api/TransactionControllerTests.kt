package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.TransactionDto
import com.betting.simplebettingapi.dto.TransactionListDto
import com.betting.simplebettingapi.helpers.TransactionType
import com.betting.simplebettingapi.model.TransactionModel
import com.betting.simplebettingapi.service.TransactionService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.math.BigDecimal
import java.time.Instant

@WebMvcTest(controllers = [TransactionController::class])
class TransactionControllerTests (
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val mapper: ObjectMapper
) {
    @MockkBean
    private lateinit var transactionService: TransactionService

    @Test
    fun givenAccountId_WhenAccountExists_ThenReturnTransactionList(){
        //given
        val accountId: Long = 1

        val transactionDto1 = TransactionDto(
            Instant.now(),
            TransactionType.INITIAL_DEPOSIT,
            BigDecimal(111),
            BigDecimal(50),
            BigDecimal(161)
        )

        val transactionDto2 = TransactionDto(
            Instant.now(),
            TransactionType.BET_PLACEMENT,
            BigDecimal(-200),
            BigDecimal(500),
            BigDecimal(300)
        )

        val transactionListDto = TransactionListDto(listOf(transactionDto1, transactionDto1))
        val transactionListDtoStr = mapper.writeValueAsString(transactionListDto)

        every{ transactionService.getTransactionsByAccountId(accountId) } returns transactionListDto

        //when / then
        mockMvc.get("/api/v1/account/$accountId/transactions") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(transactionListDtoStr) }
        }
    }
}