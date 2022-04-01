package uz.magnumactive.benefit

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("Firebase", "New Data from Firebase: ${message.data}")

    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }
}