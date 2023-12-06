package com.example.validatewithcleanarchitectureconcept.domain.use_case.common

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
