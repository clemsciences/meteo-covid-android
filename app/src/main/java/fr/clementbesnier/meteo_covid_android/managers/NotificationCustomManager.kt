package fr.clementbesnier.meteo_covid_android.managers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.activities.WebViewActivity


object NotificationCustomManager {
    val NEW_REPORT_CHANNEL_ID = "NEW_REPORT_CHANNEL_ID"

    fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.report_channel)
        val descriptionText = context.getString(R.string.report_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NotificationCustomManager::NEW_REPORT_CHANNEL_ID.toString(), name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    fun createNewReportMeteoCovidNotification(context: Context): Notification {
        val intent = Intent(context, WebViewActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, NEW_REPORT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("Nouveau rapport disponible")
            .setContentText("Appuyez ici pour consulter votre nouveau rapport personnalisé")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        return builder.build()
    }

    fun showNotif(context: Context, notifType: String) {
        val notif = when(notifType) {
            "new_report" -> createNewReportMeteoCovidNotification(context)
            else -> null
        }
        if(notif != null) {
            with(NotificationManagerCompat.from(context)) {
                notify(1, notif)
            }
        }
    }
}