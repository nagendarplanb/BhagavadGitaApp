package com.example.bhagavadgitaapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class LanguageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        // Spinner for selecting language
        val languageSpinner: Spinner = findViewById(R.id.languageSpinner)
        val languages = arrayOf("Telugu", "Hindi", "Marathi", "Kannada", "Tamil", "Malayalam", "Bhojpuri", "Konkini")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        // Button to proceed after language selection
        val getStartedButton: Button = findViewById(R.id.getStartedButton)

        getStartedButton.setOnClickListener {
            val selectedLanguage = languageSpinner.selectedItem.toString()

            // Save selected language in SharedPreferences
            val sharedPreferences: SharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("selectedLanguage", selectedLanguage)
            editor.apply()

            // Move to main menu screen
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
