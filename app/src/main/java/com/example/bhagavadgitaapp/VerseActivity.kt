package com.example.bhagavadgitaapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import org.json.JSONObject
import android.graphics.Color

class VerseActivity : AppCompatActivity() {

    private lateinit var textSanskrit: TextView
    private lateinit var textEnglish: TextView
    private lateinit var textSelectedLanguage: TextView
    private lateinit var buttonPrevious: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var verseTitle: TextView

    private var currentVerse = 1
    private val totalVerses = 47
    private var chapterNumber = 1
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verse)

        verseTitle = findViewById(R.id.verseTitle)
        textSanskrit = findViewById(R.id.sanskritVerse)
        textEnglish = findViewById(R.id.englishTranslation)
        textSelectedLanguage = findViewById(R.id.selectedLanguageTranslation)
        buttonPrevious = findViewById(R.id.previousButton)
        buttonNext = findViewById(R.id.nextButton)
        buttonPlayPause = findViewById(R.id.buttonPlayPause)

        chapterNumber = intent.getIntExtra("CHAPTER_NUMBER", 1)
        currentVerse = intent.getIntExtra("VERSE_NUMBER", 1)

        loadVerse()

        buttonPrevious.setOnClickListener {
            if (currentVerse > 1) {
                currentVerse--
                loadVerse()
            }
        }

        buttonNext.setOnClickListener {
            currentVerse++
            Log.d("VerseActivity", "Next Verse: $currentVerse") // ✅ Debugging log
            loadVerse() // Reload with new verse number
        }

        buttonPlayPause.setOnClickListener {
            togglePlayPause()
        }
    }

    private fun loadVerse() {

        val verseTitleTextView = findViewById<TextView>(R.id.verseTitle) // Get title TextView
        supportActionBar?.title = "Sloka $currentVerse" // ✅ Update ActionBar title
        verseTitleTextView.text = "Sloka $currentVerse" // ✅ Update TextView title

        Log.d("VerseActivity", "Loading Verse: $currentVerse") // ✅ Debugging log

        supportActionBar?.title = "Sloka $currentVerse"
        // Load JSON from assets
        val jsonString = applicationContext.assets.open("verses.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        // ✅ Directly access "verses" (since chapter is a separate key)
        val versesData = jsonObject.optJSONObject("verses")
        if (versesData == null) {
            Log.e("VerseActivity", "Error: Verses object not found!")
            return
        }

        // ✅ Ensure the verse exists in JSON
        val verseData = versesData.optJSONObject(currentVerse.toString())
        if (verseData == null) {
            Log.e("VerseActivity", "Error: Verse $currentVerse not found in JSON!")
            return
        }

        // ✅ Log retrieved Sanskrit verse for debugging
        Log.d("VerseActivity", "Verse Data: ${verseData.getString("sanskrit")}")

        // ✅ Update UI with Sanskrit, English, and selected language translation
        textSanskrit.text = verseData.getString("sanskrit")
        textEnglish.text = verseData.getString("english")

        val selectedLanguage = getSelectedLanguage() // Function to get saved user language
        textSelectedLanguage.text = verseData.optString(selectedLanguage, "Translation not available")

        // ✅ Enable/Disable Previous button correctly
        buttonPrevious.isEnabled = currentVerse > 1
        buttonPrevious.alpha = if (currentVerse > 1) 1.0f else 0.5f

        if (currentVerse > 1) {
            buttonPrevious.isEnabled = true
            buttonPrevious.setTextColor(Color.WHITE) // Match Next button text color
            buttonPrevious.backgroundTintList = ColorStateList.valueOf(Color.BLUE) // Match Next button color immediately
        } else {
            buttonPrevious.isEnabled = false
            buttonPrevious.setTextColor(Color.GRAY) // Gray text when disabled
            buttonPrevious.backgroundTintList = ColorStateList.valueOf(Color.BLUE) // Light gray background
        }

        if (currentVerse < 47) {
            buttonNext.isEnabled = true
            buttonNext.setTextColor(Color.WHITE) // Ensure enabled state is same as previous button
            buttonNext.backgroundTintList = buttonPrevious.backgroundTintList // Match Previous button color
        } else {
            buttonNext.isEnabled = false
            buttonNext.setTextColor(Color.GRAY) // Gray text when disabled
            buttonNext.backgroundTintList = ColorStateList.valueOf(Color.BLUE) // Light blue background
        }
    }

    private fun navigateToVerse(verseNumber: Int) {
        val intent = Intent(this, VerseActivity::class.java)
        intent.putExtra("CHAPTER_NUMBER", chapterNumber)
        intent.putExtra("VERSE_NUMBER", verseNumber)
        startActivity(intent)
        finish()
    }

    private fun togglePlayPause() {
        isPlaying = !isPlaying

        val fadeAnimation = AlphaAnimation(0.3f, 1.0f)
        fadeAnimation.duration = 300
        buttonPlayPause.startAnimation(fadeAnimation)

        if (isPlaying) {
            buttonPlayPause.setImageResource(R.drawable.ic_pause)
        } else {
            buttonPlayPause.setImageResource(R.drawable.ic_play)
        }
    }

    private fun getVerseData(chapter: Int, verse: Int, language: String): VerseData {
        val jsonString = applicationContext.assets.open("verses.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        val chapterData = jsonObject.getJSONObject(chapter.toString())

        if (!chapterData.has(verse.toString())) {
            throw IllegalArgumentException("Verse $verse not found in Chapter $chapter")
        }

        val verseData = chapterData.getJSONObject(verse.toString())

        return VerseData(
            sanskrit = verseData.getString("sanskrit"),
            english = verseData.getString("english"),
            selectedLanguage = verseData.optString(language, "Translation not available")
        )
    }

    private fun getSelectedLanguage(): String {
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        return sharedPreferences.getString("language", "english") ?: "english"
    }

    data class VerseData(val sanskrit: String, val english: String, val selectedLanguage: String)
}