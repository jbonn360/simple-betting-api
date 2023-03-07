package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.dto.AccountDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpStatus

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        //"spring.datasource.url=jdbc:h2:mem:testdb"
    ]
)
class AccountControllerTests(@Autowired val client: TestRestTemplate) {
    @Test
    fun testAddAccount(){
        val accountDto = AccountDto(-1, "username1", "name1", "surname1", null)

        client.postForObject<AccountDto>("/api/v1/account/", accountDto)

        val entity = client.getForEntity<AccountDto>("/api/v1/account/1")
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }
}