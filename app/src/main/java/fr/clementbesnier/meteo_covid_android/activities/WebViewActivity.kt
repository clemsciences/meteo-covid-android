package fr.clementbesnier.meteo_covid_android.activities

import android.annotation.SuppressLint
import android.content.Context
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
        mcWebViewActivity.setContext(this.applicationContext)
        meteoCovidWebView.webViewClient = mcWebViewActivity
        meteoCovidWebView.settings.javaScriptEnabled = true
        this.applicationContext

    }


}

class MeteoCovidWebViewClient: WebViewClient() {
    lateinit var mContext: Context
    fun setContext(context: Context) {
        mContext = context
    }
    fun getContext() {
        mContext
    }
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        super.shouldOverrideUrlLoading(view, request)

        if(MAIN_URL == Uri.parse(request.toString()).toString()) {
            return false
        }
        Intent(Intent.ACTION_VIEW, Uri.parse(request.toString())).apply {
            startActivity(mContext, this, null)
        }

        return true

    }

}