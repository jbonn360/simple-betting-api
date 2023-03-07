package com.betting.simplebettingapi.api

import com.betting.simplebettingapi.exception.InsufficientCreditsException
import com.betting.simplebettingapi.exception.InvalidTransactionException
import com.betting.simplebettingapi.exception.NumberOutOfBoundsException
import org.springframework.boot.context.properties.bind.BindException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class MvcExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundErrorHandler(ex: EntityNotFoundException): ResponseEntity<String> {
        val error = if (ex.message == null) "Entity was not found in database" else ex.message!!

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InsufficientCreditsException::class)
    fun insufficientCreditsErrorHandler(ex: InsufficientCreditsException): ResponseEntity<String> {
        val error = if (ex.message == null) "Insufficient credits to perform operation" else ex.message!!

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidTransactionException::class)
    fun invalidTransactionErrorHandler(ex: InvalidTransactionException): ResponseEntity<String> {
        val error = if (ex.message == null) "Transaction is invalid" else ex.message!!

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationErrorHandler(ex: ConstraintViolationException): ResponseEntity<List<String>> {
        val errors = ArrayList<String>(ex.constraintViolations.size)

        ex.constraintViolations.forEach { violation ->
            errors.add("${violation.propertyPath} : ${violation.message}")
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}