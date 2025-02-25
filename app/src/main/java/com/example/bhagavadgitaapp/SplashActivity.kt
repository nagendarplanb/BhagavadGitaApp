package com.example.bhagavadgitaapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView  // ✅ Import this
import android.widget.TextView  // ✅ Import this
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // ✅ Now ImageView and TextView will be recognized
        val krishnaImageView: ImageView = findViewById(R.id.krishnaImageView)

        // Set Krishna Image
        krishnaImageView.setImageResource(R.drawable.krishna)

        // Move to Login Screen after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3-second delay
    }
}
