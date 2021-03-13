package fr.clementbesnier.meteo_covid_android.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import fr.clementbesnier.meteo_covid_android.R
import fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS
import fr.clementbesnier.meteo_covid_android.constants.USER_SETTINGS


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var spinner: Spinner
    lateinit var saveButton: Button
    var selectedDepartementId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedDepartementId = -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)


        spinner = view.findViewById<Spinner>(R.id.departement_chooser_spinner)
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.departements_noms,
                android.R.layout.simple_spinner_item)
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        saveButton = view.findViewById(R.id.saveSettingsButton)
        saveButton.setOnClickListener {
            activity?.let {
                with(it.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)?.edit()) {
                    this?.putInt(fr.clementbesnier.meteo_covid_android.constants.DEPARTEMENT_ID_SETTINGS, spinner.selectedItemPosition)
                    this?.apply()
                    println("id saved ${spinner.selectedItemPosition}")
                    android.widget.Toast.makeText(it, "Paramètres sauvegardés", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }

        loadCurrentConfig()
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDepartementId = position
        println("position $position")
        println("id $id")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun loadCurrentConfig() {

        activity?.let {
            it.getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE).apply {
            selectedDepartementId = this.getInt(DEPARTEMENT_ID_SETTINGS, 0)
            resources.getStringArray(R.array.departements_id)[selectedDepartementId]
            spinner.setSelection(selectedDepartementId)
        }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance() =
                SettingsFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}