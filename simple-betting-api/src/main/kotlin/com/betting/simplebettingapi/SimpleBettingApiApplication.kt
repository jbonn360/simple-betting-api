package com.betting.simplebettingapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableScheduling
@EnableCaching
@SpringBootApplication
class SimpleBettingApiApplication

fun main(args: Array<String>) {
	runApplication<SimpleBettingApiApplication>(*args)
}
