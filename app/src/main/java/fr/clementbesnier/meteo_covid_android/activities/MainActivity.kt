package fr.clementbesnier.meteo_covid_android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.fragments.AboutFragment
import fr.clementbesnier.meteo_covid_android.fragments.SettingsFragment
import fr.clementbesnier.meteo_covid_android.fragments.WebsiteFragment
import fr.clementbesnier.meteo_covid_android.managers.NotificationCustomManager

class MainActivity : AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener {
    private val websiteFragment = WebsiteFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()
    private val aboutFragment = AboutFragment.newInstance()
    private var selectedFragment: Fragment = websiteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationCustomManager.createNotificationChannel(this)
        FirebaseMessaging.getInstance().subscribeToTopic("report")

        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainLayout, websiteFragment)
            .add(R.id.mainLayout, settingsFragment).hide(settingsFragment)
            .add(R.id.mainLayout, aboutFragment).hide(aboutFragment)
            .commit()


    }

    override fun onStart() {
        super.onStart()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
//        bottomNavigation.selectedItemId = selectedFragmentId
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        selectedFragmentId = item.itemId
        return goToView(item.itemId)
    }

    private fun goToView(id: Int): Boolean {
        val fm = supportFragmentManager.beginTransaction()
        when (id) {
            R.id.page_1 -> {
                println("web site")
                fm.hide(selectedFragment).show(websiteFragment).commit()
                selectedFragment = websiteFragment
                return true
            }
            R.id.page_2 -> {
                println("settings")
                fm.hide(selectedFragment).show(settingsFragment).commit()
                selectedFragment = settingsFragment
                return true
            }
            R.id.page_3 -> {
                println("about")
                fm.hide(selectedFragment).show(aboutFragment).commit()
                selectedFragment = aboutFragment
                return true
            }
        }
        return false
    }
}