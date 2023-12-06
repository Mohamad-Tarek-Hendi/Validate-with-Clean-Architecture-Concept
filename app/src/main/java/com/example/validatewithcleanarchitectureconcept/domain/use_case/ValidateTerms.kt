package com.example.validatewithcleanarchitectureconcept.domain.use_case

import com.example.validatewithcleanarchitectureconcept.domain.use_case.common.ValidationResult

class ValidateTerms {

    fun execute(acceptTerms: Boolean): ValidationResult {
        if (!acceptTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}