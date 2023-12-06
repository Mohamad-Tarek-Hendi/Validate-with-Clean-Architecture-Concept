package com.example.validatewithcleanarchitectureconcept.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.validatewithcleanarchitectureconcept.domain.use_case.ValidateEmail
import com.example.validatewithcleanarchitectureconcept.domain.use_case.ValidatePassword
import com.example.validatewithcleanarchitectureconcept.domain.use_case.ValidateRepeatedPassword
import com.example.validatewithcleanarchitectureconcept.domain.use_case.ValidateTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms(),
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
    private val _validateEventChannel = Channel<ValidationEvent>()
    val validateEventChannel = _validateEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email
                )
            }

            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password
                )
            }

            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(
                    repeatedPassword = event.repeatedPassword
                )
            }

            is RegistrationFormEvent.AcceptableTermsChanged -> {
                state = state.copy(
                    acceptedTerms = event.acceptableTerms
                )
            }

            RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(email = state.email)
        val passwordResult = validatePassword.execute(password = state.password)
        val repeatedPasswordResult =
            validateRepeatedPassword.execute(
                repeatedPassword = state.repeatedPassword,
                password = state.password
            )
        val termsResult = validateTerms.execute(acceptTerms = state.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any() { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            _validateEventChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}