package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.exception.InvalidTransactionException
import com.betting.simplebettingapi.exception.EntityNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class MvcExceptionHandler() {
    private val jsonHeaders = HttpHeaders()

    init {
        jsonHeaders.contentType = MediaType.APPLICATION_JSON
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundErrorHandler(ex: EntityNotFoundException): ResponseEntity<String> {
        val error = if (ex.message == null) "Entity was not found in database" else ex.message!!

        return ResponseEntity(error, jsonHeaders, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientCreditsException::class)
    fun insufficientCreditsErrorHandler(ex: InsufficientCreditsException): ResponseEntity<String> {
        val error = if (ex.message == null) "Insufficient credits to perform operation" else ex.message!!

        return ResponseEntity(error, jsonHeaders, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidTransactionException::class)
    fun invalidTransactionErrorHandler(ex: InvalidTransactionException): ResponseEntity<String> {
        val error = if (ex.message == null) "Transaction is invalid" else ex.message!!

        return ResponseEntity(error, jsonHeaders, HttpStatus.BAD_REQUEST)
    }
}