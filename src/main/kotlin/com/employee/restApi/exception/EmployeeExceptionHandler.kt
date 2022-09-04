package com.employee.restApi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class EmployeeExceptionHandler {

    @ExceptionHandler
    fun handleException(ex: EmployeeNotFoundException) : ResponseEntity<EmployeeErrorResponse> {
        val errorResponse = EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message, System.currentTimeMillis())
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleGenericException(ex: Exception) : ResponseEntity<EmployeeErrorResponse> {
        val errorResponse = EmployeeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message, System.currentTimeMillis())
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

class EmployeeNotFoundException(message: String): RuntimeException(message)

class EmployeeErrorResponse(val status: Int, val message: String? = null, val timeStamp: Long)