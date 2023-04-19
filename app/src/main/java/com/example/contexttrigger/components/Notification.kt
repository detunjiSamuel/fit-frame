package com.example.contexttrigger.components


import android.app.NotificationManager
import android.content.Context

import android.util.Log
import androidx.core.app.NotificationCompat

// error might come from here - Andriod studio generated
import com.example.contexttrigger.R


class Notification {

    private fun fireEvent(context: Context, id: Int, channelId: String, title: String, message: String) {
        var notification: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)

        val notify: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d("Notification-handler", "Notification Sent")
        notify.notify(id, notification.build())
    }

}