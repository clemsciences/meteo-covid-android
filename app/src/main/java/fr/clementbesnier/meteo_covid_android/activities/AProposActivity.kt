package fr.clementbesnier.meteo_covid_android.activities

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.MAIN_URL

class AProposActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_propos)

        val aboutWebView = findViewById<WebView>(R.id.aboutWebView)
        aboutWebView.loadUrl("file:///android_asset/about.html")

    }
}