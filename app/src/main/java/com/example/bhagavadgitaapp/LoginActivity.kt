package com.example.bhagavadgitaapp

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Find Views
        val usernameInput: EditText = findViewById(R.id.usernameInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpText: TextView = findViewById(R.id.signUpText)

        // Apply Emoji Filter
        usernameInput.filters = arrayOf(blockEmojis())
        passwordInput.filters = arrayOf(blockEmojis())

        // Handle Login Button Click
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Navigate to Main Menu
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Handle Sign-Up Click
        signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to Block Emojis
    private fun blockEmojis(): InputFilter {
        return object : InputFilter {
            override fun filter(
                source: CharSequence?, start: Int, end: Int,
                dest: Spanned?, dstart: Int, dend: Int
            ): CharSequence? {
                source?.forEach {
                    if (!Character.isLetterOrDigit(it) && !Character.isWhitespace(it)) {
                        return ""
                    }
                }
                return null
            }
        }
    }
}
