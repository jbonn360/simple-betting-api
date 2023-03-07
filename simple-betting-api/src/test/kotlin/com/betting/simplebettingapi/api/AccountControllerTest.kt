package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import com.betting.simplebettingapi.repository.AccountRepository
import com.betting.simplebettingapi.repository.WalletRepository
import com.betting.simplebettingapi.service.AccountService
import com.betting.simplebettingapi.service.WalletService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

//@SpringBootTest
//@WebMvcTest(AccountController::class)
//@ContextConfiguration(classes = [AccountService::class, WalletService::class, WalletRepository::class, AccountRepository::class])
//@ComponentScan(basePackages = ["com.betting.simplebettingapi"])
class AccountControllerTest(@Autowired private val mockMvc: MockMvc,
                            @Autowired private val mapper: ObjectMapper) {

//    @Test
//    fun createNewAccount() {
//        val accountDto = AccountDto(-1, "username1", "name1", "surname1", null)
//        val accountStr = mapper.writeValueAsString(accountDto)
//
//        mockMvc.post("/api/v1/account/"){
//            contentType = MediaType.APPLICATION_JSON
//            content = accountStr
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { isCreated() }
//        }
//    }

    //@Test
//    fun getAccountById() {
//        mockMvc
//    }


}