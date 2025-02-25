package com.example.bhagavadgitaapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        val chapterNumber = intent.getIntExtra("CHAPTER_NUMBER", 1)
        val chapterTitle: TextView = findViewById(R.id.chapterTitle)
        chapterTitle.text = "Chapter $chapterNumber"
    }
}
