package fr.clementbesnier.meteo_covid_android.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.core.content.ContextCompat
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.DEFAULT_DEPARTEMENT
import fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS
import fr.clementbesnier.meteo_covid_android.constants.MAIN_URL
import fr.clementbesnier.meteo_covid_android.constants.USER_SETTINGS


/**
 * A simple [Fragment] subclass.
 * Use the [WebsiteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebsiteFragment : Fragment() {

    lateinit var meteoCovidWebView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("web site view created")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_website, container, false)
        meteoCovidWebView = view.findViewById<WebView>(R.id.meteoCovidWebView)
        if(savedInstanceState != null) {
            println("restore")
            meteoCovidWebView.restoreState(savedInstanceState)
        } else {
            println("load")
            meteoCovidWebView.loadUrl("https://$MAIN_URL")
        }
        val mcWebViewActivity = MeteoCovidWebViewClient()
        meteoCovidWebView.webViewClient = mcWebViewActivity
        meteoCovidWebView.settings.javaScriptEnabled = true

        view.findViewById<Button>(R.id.buttonMyDepartment)?.setOnClickListener {
            val departementId = retrieveSelectedDepartment()
            meteoCovidWebView.loadUrl("https://$MAIN_URL/dept-mob-$departementId")
        }

        val buttonPrevious = view.findViewById<Button>(R.id.buttonPrevious)
        buttonPrevious?.setOnClickListener{
            meteoCovidWebView.goBack()
//            it.isEnabled = meteoCovidWebView.canGoBack()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        meteoCovidWebView.saveState(outState)
    }

    inner class MeteoCovidWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            super.shouldOverrideUrlLoading(view, request)
            context?.let {
                if(request != null) {
                    if (MAIN_URL == request.url.host) {
                        return false
                    }
                    Intent(Intent.ACTION_VIEW, request.url).apply {
                        ContextCompat.startActivity(it, this, null)
                    }
                }
            }
            return true
        }
    }

    private fun retrieveSelectedDepartment(): String {
        activity?.let {
            val preferences = it.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)
            val selectedDepartementId = preferences.getInt(DEPARTEMENT_ID_SETTINGS, DEFAULT_DEPARTEMENT) // default department is Indre-et-Loire
            println("id du département $selectedDepartementId")
            return resources.getStringArray(R.array.departements_id)[selectedDepartementId]
        }
        return resources.getStringArray(R.array.departements_id)[37]
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            meteoCovidWebView.restoreState(it)
        }
    }

    override fun onStart() {
        super.onStart()
        println("on start")
    }

    override fun onStop() {
        super.onStop()
        println("on stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("on destroy")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WebsiteFragment.
         */
        @JvmStatic
        fun newInstance() =
            WebsiteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}