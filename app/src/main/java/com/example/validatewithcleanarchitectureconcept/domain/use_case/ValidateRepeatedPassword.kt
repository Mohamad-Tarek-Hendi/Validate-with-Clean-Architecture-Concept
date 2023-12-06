package com.example.validatewithcleanarchitectureconcept.domain.use_case

import com.example.validatewithcleanarchitectureconcept.domain.use_case.common.ValidationResult

class ValidateRepeatedPassword {

    fun execute(repeatedPassword: String, password: String): ValidationResult {
        if (repeatedPassword != password) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}