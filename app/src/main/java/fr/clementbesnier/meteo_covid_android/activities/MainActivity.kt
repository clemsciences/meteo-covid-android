package fr.clementbesnier.meteo_covid_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.managers.NotificationCustomManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val webViewButton = findViewById<Button>(R.id.buttonStatistics)
        val aboutButton = findViewById<Button>(R.id.buttonAbout)
        NotificationCustomManager.createNotificationChannel(baseContext)

        buttonSettings.setOnClickListener {
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }
        }

        webViewButton.setOnClickListener {
            Intent(this, WebViewActivity::class.java).apply {
                startActivity(this)
            }
        }

        aboutButton.setOnClickListener {
            Intent(this, AProposActivity::class.java).apply{
                startActivity(this)
            }
        }
    }
}