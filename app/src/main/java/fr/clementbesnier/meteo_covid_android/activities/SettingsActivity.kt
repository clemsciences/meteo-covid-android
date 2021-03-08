package fr.clementbesnier.meteo_covid_android.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS
import fr.clementbesnier.meteo_covid_android.constants.USER_SETTINGS


class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var spinner: Spinner
    lateinit var saveButton: Button
    var selectedDepartementId: Int = -1
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        selectedDepartementId = -1
        sharedPreferences = getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)
    }


    override fun onStart() {
        super.onStart()
        spinner = findViewById<Spinner>(R.id.departement_chooser_spinner)
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.departements_noms,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        saveButton = findViewById(R.id.saveSettingsButton)
        saveButton.setOnClickListener {
            with(sharedPreferences.edit()) {
                this.putInt(DEPARTEMENT_ID_SETTINGS, spinner.selectedItemPosition)
                this.apply()
                println("id saved ${spinner.selectedItemPosition}")
                Toast.makeText(this@SettingsActivity, "Paramètres sauvegardés", Toast.LENGTH_LONG).show()
            }
        }

        loadCurrentConfig()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDepartementId = position
        println("position $position")
        println("id $id")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun loadCurrentConfig() {
        sharedPreferences.apply {
            selectedDepartementId = this.getInt(DEPARTEMENT_ID_SETTINGS, 0)
            resources.getStringArray(R.array.departements_id)[selectedDepartementId]
            spinner.setSelection(selectedDepartementId)
        }
    }
}