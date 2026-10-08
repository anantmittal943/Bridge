package com.anantmittal.bridge.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.anantmittal.bridge.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("EXTRA_REMINDER_TITLE") ?: "Reminder"
        val id = intent?.getStringExtra("EXTRA_REMINDER_ID") ?: return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "REMINDER_CHANNEL_ID",
                "Work Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "REMINDER_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("It's time for your task!")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id.hashCode(), notification)

        // Note: For a truly repeating daily alarm, AlarmManager.setRepeating could be used 
        // or we reschedule it here. For simplicity and exactness, rescheduling is often better on Android >= 6.
        // We can just rely on the user seeing it or if needed schedule the next day.
    }
}
