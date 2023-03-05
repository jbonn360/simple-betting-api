package com.betting.simplebettingapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
class SimpleBettingApiApplication

fun main(args: Array<String>) {
	runApplication<SimpleBettingApiApplication>(*args)
}
