package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.dto.WalletDto
import com.betting.simplebettingapi.exception.EntityNotFoundException
import com.betting.simplebettingapi.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal

@WebMvcTest(controllers = [AccountController::class])
class AccountControllerTests(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val mapper: ObjectMapper
) {
    @MockkBean
    private lateinit var accountService: AccountService

    private val accountDto = AccountDto(
        1,
        "username1",
        "name1",
        "surname1",
        null
    )

    @Test
    fun givenAccountId_WhenAccountExists_ThenAccountIsReturned() {
        //given
        val accountId: Long = 1

        every {
            accountService.getAccountById(accountId)
        } returns accountDto

        val accountDtoStr = mapper.writeValueAsString(accountDto)

        //when / then
        mockMvc.get("/api/v1/account/$accountId") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(accountDtoStr) }
        }
    }

    @Test
    fun givenAccountId_WhenAccountNotExists_ThenErrorIsReturned() {
        val accountId: Long = 100

        //given
        every {
            accountService.getAccountById(accountId)
        } throws EntityNotFoundException("Account not found")

        //when / then
        mockMvc.get("/api/v1/account/$accountId") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun givenAccountDto_WhenCreateAccountIsCalled_AccountIsCreated() {
        //given
        every { accountService.createAccount(any()) } returns accountDto.id

        //when / then
        mockMvc.post("/api/v1/account") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(accountDto)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            header { string("Location", "/api/v1/account/${accountDto.id}") }
        }
    }

    @Test
    fun givenInvalidAccountDto_WhenCreateAccountIsCalled_ErrorIsShown() {
        //given
        val invalidAccount = AccountDto(
            1,
            "a",
            "a",
            "a",
            null
        )

        //when / then
        mockMvc.post("/api/v1/account") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(invalidAccount)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }
}