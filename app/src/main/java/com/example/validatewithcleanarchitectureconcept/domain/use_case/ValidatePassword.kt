package com.example.validatewithcleanarchitectureconcept.domain.use_case

import com.example.validatewithcleanarchitectureconcept.domain.use_case.common.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password can't be very short."
            )
        }

        val isContainsLetterAndDigit =
            password.any() { it.isDigit() } && password.any() { it.isLetter() }

        if (!isContainsLetterAndDigit) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain at least one letter and digit"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}