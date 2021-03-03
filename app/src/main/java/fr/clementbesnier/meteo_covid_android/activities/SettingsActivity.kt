package fr.clementbesnier.meteo_covid_android.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS


class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var spinner: Spinner
    lateinit var saveButton: Button
    var selectedDepartementId: Int = -1
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        selectedDepartementId = -1
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
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
                println(selectedDepartementId)
                putInt(DEPARTEMENT_ID_SETTINGS, spinner.selectedItemPosition)
                this.apply()
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