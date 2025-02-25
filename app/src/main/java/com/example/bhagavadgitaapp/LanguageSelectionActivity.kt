package com.example.bhagavadgitaapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LanguageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        val languageSpinner: Spinner = findViewById(R.id.languageSpinner)
        val continueButton: Button = findViewById(R.id.continueButton)

        // ✅ Add Language Options to Spinner
        val languages = arrayOf("Select Language", "Hindi", "Telugu", "Tamil", "Kannada", "Bengali")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        languageSpinner.adapter = adapter

        continueButton.setOnClickListener {
            val selectedLanguage = languageSpinner.selectedItem.toString()

            if (selectedLanguage != "Select Language") {
                // ✅ Convert language name to lowercase before saving
                val sharedPreferences: SharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("language", selectedLanguage.lowercase()) // Fix key and lowercase
                editor.apply()

                // ✅ Move to MainMenuActivity
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
