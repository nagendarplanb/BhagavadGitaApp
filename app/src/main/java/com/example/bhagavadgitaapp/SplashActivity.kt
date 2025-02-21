package com.example.bhagavadgitaapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 1. Find ImageView and TextView for Krishna and the app name
        val krishnaImageView: ImageView = findViewById(R.id.krishnaImageView)
        val appNameTextView: TextView = findViewById(R.id.appNameTextView)

        // 2. Set the animation for the spinning Vishnu Chakra (GIF/SVG)
        krishnaImageView.setImageResource(R.drawable.krishna)  // Put your GIF/SVG here

        // 3. Set up the app name animation
        val appNameText = arrayOf("भगवद गीता", "Bhagavad Gita", "భగవద్గీత", "ಭಗವದ್ಗೀತೆ", "भगवद गीता", "ಭಗವದ್ಗೀತ", "பகவத்கீதை", "ഭഗവത്ഗീത", "भगवद गीता") // Add more languages

        val handler = Handler()
        val interval: Long = 500 // Interval in milliseconds

        var index = 0
        val runTextAnimation = object : Runnable {
            override fun run() {
                appNameTextView.text = appNameText[index]
                index++
                if (index < appNameText.size) {
                    handler.postDelayed(this, interval)
                } else {
                    // Proceed to next screen after the animation
                    Handler().postDelayed({
                        val intent = Intent(this@SplashActivity, LanguageSelectionActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, interval)
                }
            }
        }

        // Start the animation
        handler.post(runTextAnimation)
    }
}
