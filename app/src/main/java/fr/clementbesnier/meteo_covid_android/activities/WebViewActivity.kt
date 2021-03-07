package fr.clementbesnier.meteo_covid_android.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.MAIN_URL


class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val meteoCovidWebView = findViewById<WebView>(R.id.meteoCovidWebView)
        meteoCovidWebView.loadUrl("https://www.meteo-covid.com/")
        val mcWebViewActivity = MeteoCovidWebViewClient()
        meteoCovidWebView.webViewClient = mcWebViewActivity
        meteoCovidWebView.settings.javaScriptEnabled = true
    }

    inner class MeteoCovidWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            super.shouldOverrideUrlLoading(view, request)
            if(MAIN_URL == Uri.parse(request.toString()).toString()) {
                return false
            }
            Intent(Intent.ACTION_VIEW, Uri.parse(request.toString())).apply {
                startActivity(this@WebViewActivity, this, null)
            }
            return true
        }
    }
}
