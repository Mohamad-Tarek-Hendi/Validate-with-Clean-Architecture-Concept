package com.example.validatewithcleanarchitectureconcept.domain.use_case

import android.util.Patterns
import com.example.validatewithcleanarchitectureconcept.domain.use_case.common.ValidationResult

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That not valid email"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}