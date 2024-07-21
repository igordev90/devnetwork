package com.devnetwork.app.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.devnetwork.app.firebase.FirebaseRepository
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    var errorMessage by mutableStateOf("")
    var _loading by mutableStateOf(false)
    val loading: Boolean
        get() = _loading


    fun validateAndLogin(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val isEmailValid = ValidationUtil.isEmailValid(email)
        val isPasswordValid = ValidationUtil.isPasswordValid(password)

        errorMessage = if (isEmailValid && isPasswordValid) {
            showLoading()
            signIn(email, password, callback)
            "" // Clear any previous error message
        } else {
            // Set error message based on validation results
            when {
                email.isBlank() -> "Email field cannot be empty"
                password.isBlank() -> "Password field cannot be empty"
                !isEmailValid -> "Invalid email address"
                !isPasswordValid -> "Password must be at least 6 characters"
                else -> ""
            }
        }
    }

    fun validateAndSignUp(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val isNameEmpty = name.isBlank()
        val isUserNameEmpty = username.isBlank()
        val isEmailEmpty = email.isBlank()
        val isEmailValid = ValidationUtil.isEmailValid(email)
        val isPasswordEmpty = password.isBlank()
        val isPasswordValid = ValidationUtil.isPasswordValid(password)
        val isConfirmPasswordEmpty = confirmPassword.isBlank()
        val isBothPasswordsSame = password.lowercase() == confirmPassword.lowercase()

        errorMessage = if (
            !isNameEmpty &&
            !isUserNameEmpty &&
            !isEmailEmpty &&
            isEmailValid &&
            !isPasswordEmpty &&
            isPasswordValid &&
            !isConfirmPasswordEmpty &&
            isBothPasswordsSame
            ) {
            showLoading()
            signUp(email, password, callback)
            "" // Clear any previous error message
        } else {
            // Set error message based on validation results
            when {
                isNameEmpty -> "Name Field cannot be empty"
                isUserNameEmpty -> "Username Field cannot be empty"
                isEmailEmpty -> "Email field cannot be empty"
                !isEmailValid -> "Invalid email address"
                isPasswordEmpty -> "Password field cannot be empty"
                isConfirmPasswordEmpty -> "Confirm Password field cannot be empty"
                !isPasswordValid -> "Password must be at least 6 characters"
                !isBothPasswordsSame -> "Password and confirm password are not same"
                else -> ""
            }
        }
    }

    fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseRepository.signUp(email, password, callback)
    }

    fun signIn(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseRepository.signIn(email, password, callback)
    }

    fun saveUserData(user: User) {
        firebaseRepository.saveUserData(user)
    }

    fun checkUserAuth(): Boolean {
        return firebaseRepository.checkUserAuth()
    }

    fun getLoggedUserId(): String {
        return firebaseRepository.getLoggedUserId()
    }

    fun showLoading() {
        _loading = true
    }

    fun hideLoading() {
        _loading = false
    }
}