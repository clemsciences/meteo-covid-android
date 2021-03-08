package fr.clementbesnier.meteo_covid_android.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS
import fr.clementbesnier.meteo_covid_android.constants.MAIN_URL
import fr.clementbesnier.meteo_covid_android.constants.USER_SETTINGS


class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val meteoCovidWebView = findViewById<WebView>(R.id.meteoCovidWebView)
        meteoCovidWebView.loadUrl("https://$MAIN_URL")
        val mcWebViewActivity = MeteoCovidWebViewClient()
        meteoCovidWebView.webViewClient = mcWebViewActivity
        meteoCovidWebView.settings.javaScriptEnabled = true


        findViewById<Button>(R.id.buttonMyDepartment)?.setOnClickListener {
            val departementId = retrieveSelectedDepartment()
            meteoCovidWebView.loadUrl("https://$MAIN_URL/departement-$departementId")
        }


        val buttonPrevious = findViewById<Button>(R.id.buttonPrevious)
        buttonPrevious?.setOnClickListener{
            meteoCovidWebView.goBack()
//            it.isEnabled = meteoCovidWebView.canGoBack()
        }
//        buttonPrevious?.isEnabled = false

    }

    inner class MeteoCovidWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            super.shouldOverrideUrlLoading(view, request)
            if(request != null) {
                if (MAIN_URL == request.url.host) {
                    return false
                }
                Intent(Intent.ACTION_VIEW, request.url).apply {
                    startActivity(this@WebViewActivity, this, null)
                }
            }
            return true
        }
    }

    private fun retrieveSelectedDepartment(): String {
        val preferences = getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)
        val selectedDepartementId = preferences.getInt(DEPARTEMENT_ID_SETTINGS, 37) // default department is Indre-et-Loire
        println("id du département $selectedDepartementId")
        return resources.getStringArray(R.array.departements_id)[selectedDepartementId]
    }
}
