package uz.magnumactive.benefit.ui.main.pin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager

class BiometricAuth(private val activity: AppCompatActivity?) {

    fun isBiometricSupported(): Boolean {
        //Biometric Authentication is only available from API level 23
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val biometricManager = BiometricManager.from(activity)
            return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }

}