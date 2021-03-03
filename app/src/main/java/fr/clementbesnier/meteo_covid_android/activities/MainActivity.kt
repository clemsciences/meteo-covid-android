package fr.clementbesnier.meteo_covid_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.clementbesnier.meteo_covid_android.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val webViewButton = findViewById<Button>(R.id.buttonStatistics)

        buttonSettings.setOnClickListener {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }

        webViewButton.setOnClickListener {
            val i = Intent(this, WebViewActivity::class.java)
            startActivity(i)
        }
    }
}