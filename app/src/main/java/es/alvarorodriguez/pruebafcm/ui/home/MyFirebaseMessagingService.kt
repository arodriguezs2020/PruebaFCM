package es.alvarorodriguez.pruebafcm.ui.home

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.ui.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "From: ${message.from}")

        if (message.data.isNullOrEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
        }

        message.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title!!, it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification(messageTitle: String, messageBody: String) {
        val pendingIntent = clickNotification()
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = builderNotification(
            channelId,
            messageTitle,
            messageBody,
            defaultSoundUri,
            pendingIntent
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        versionOreo(channelId, notificationManager)
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun versionOreo(
        channelId: String,
        notificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun builderNotification(
        channelId: String,
        messageTitle: String,
        messageBody: String,
        defaultSoundUri: Uri?,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(messageTitle)
            .setColorized(false)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setVisibility(VISIBILITY_PUBLIC)
            .setFullScreenIntent(pendingIntent, true)
            .setWhen(System.currentTimeMillis())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun clickNotification(): PendingIntent? {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}

