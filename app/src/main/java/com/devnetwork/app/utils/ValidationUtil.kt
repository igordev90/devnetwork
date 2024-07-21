package com.devnetwork.app.utils

import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

object ValidationUtil {
    private var isDialogVisible by mutableStateOf(false)

    fun isEmpty(value: String): Boolean {
        return value.trim().isEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    @Composable
    fun showValidationDialog(context: Context, message: String) {
        isDialogVisible = true
        val density = LocalDensity.current.density
        val dialogWidth = (300 * density).toInt()
        val dialogHeight = (150 * density).toInt()

        DisposableEffect(context) {
            onDispose {
                isDialogVisible = false
            }
        }

        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            title = { Text("Validation Error") },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { isDialogVisible = false }) {
                    Text("OK")
                }
            },
            modifier = Modifier.width(dialogWidth.dp).height(dialogHeight.dp)
        )
    }

    @Composable
    fun ValidationDialog() {
        val context = LocalContext.current
        if (isDialogVisible) {
            DisposableEffect(context) {
                onDispose {
                    isDialogVisible = false
                }
            }
            showValidationDialog(context, "Please enter valid information.")
        }
    }
}