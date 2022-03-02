package com.example.benefit.ui.main.pin

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

object BiometricPromptUtils {
    fun createBiometricPrompt(
        activity: AppCompatActivity,
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
        onUsePin: () -> Unit,
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                if (errCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    //User wants to authenticate with PIN
                    onUsePin()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess(result)
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }
}