package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.service.AccountService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

//@WebMvcTest
class AccountControllerTest2(@Autowired val mockMvc: MockMvc) {
//    @MockkBean
//    lateinit var accountService: AccountService
//
//    @Test
//    fun givenExistingAccount_whenGetRequest_thenReturnsAccountJsonWithStatus200(){
//        every { accountService.getAccountById(1) } returns AccountDto
//
//        mockMvc.perform(get("/api/bankAccount?id=1"))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.bankCode").value("ING"));
//    }
}