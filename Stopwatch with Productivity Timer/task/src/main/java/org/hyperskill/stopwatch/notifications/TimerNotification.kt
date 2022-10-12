package org.hyperskill.stopwatch.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class TimerNotification(context: Context) {

    private val channelId = "org.hyperskill"

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "TimerNotification", NotificationManager.IMPORTANCE_HIGH)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notifyOnUpperLimit(context: Context) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Timer has reached limit!")
            .setContentText("The timer has reached the limit")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setOnlyAlertOnce(true)
            .build()
        notification.flags = notification.flags or Notification.FLAG_INSISTENT
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(393939, notification)
    }
}