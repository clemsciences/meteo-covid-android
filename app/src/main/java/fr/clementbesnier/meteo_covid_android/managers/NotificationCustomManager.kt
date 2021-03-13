package fr.clementbesnier.meteo_covid_android.managers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.activities.MainActivity


object NotificationCustomManager {

    private const val TAG = "NotificationCustomManager"

    const val NEW_REPORT_CHANNEL_ID = "NEW_REPORT_CHANNEL_ID"
    const val REPORT_NOTIFICATION_TYPE = "REPORT_NOTIFICATION_TYPE"


    fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.report_channel)
        val descriptionText = context.getString(R.string.report_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NEW_REPORT_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    private fun createNewReportMeteoCovidNotification(context: Context, text: String? ="Appuyez ici pour consulter votre nouveau rapport personnalisé"): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, NEW_REPORT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Nouveau rapport disponible")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setChannelId(NEW_REPORT_CHANNEL_ID)
            .setAutoCancel(false)
        if(text != null) {
            builder.setContentText(text)
        }
        return builder.build()
    }

    fun showNotif(context: Context, notifType: String, text: String?) {
        val notif = when(notifType) {
            REPORT_NOTIFICATION_TYPE -> createNewReportMeteoCovidNotification(context, text)
            else -> null
        }
        if(notif != null) {
            with(NotificationManagerCompat.from(context)) {
                notify(1, notif)
            }
        } else {
            Log.d(TAG, "notif is null")
        }
    }
}