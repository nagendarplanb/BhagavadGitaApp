package com.example.bhagavadgitaapp

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerseActivity : AppCompatActivity() {

    private lateinit var textSanskrit: TextView
    private lateinit var textEnglish: TextView
    private lateinit var textSelectedLanguage: TextView
    private lateinit var buttonPrevious: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonPlayPause: ImageButton
    private lateinit var verseTitle: TextView  // New Title for Sloka

    private var currentVerse = 1
    private var totalVerses = 47
    private var chapterNumber = 1
    private var isPlaying = false // Track Play/Pause state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verse) // Ensure this matches the XML filename

        // ✅ **Make sure IDs match with `activity_verse.xml`**
        verseTitle = findViewById(R.id.verseTitle)
        textSanskrit = findViewById(R.id.sanskritVerse) // Fixed ID
        textEnglish = findViewById(R.id.englishTranslation) // Fixed ID
        textSelectedLanguage = findViewById(R.id.selectedLanguageTranslation) // Fixed ID
        buttonPrevious = findViewById(R.id.previousButton) // Fixed ID
        buttonNext = findViewById(R.id.nextButton)
        buttonPlayPause = findViewById(R.id.buttonPlayPause)

        chapterNumber = intent.getIntExtra("CHAPTER_NUMBER", -1)

        if (chapterNumber == -1) {
            textSanskrit.text = "Error: Chapter not found"
            textEnglish.text = "Please go back and try again."
            return
        }

        loadVerse()

        buttonPrevious.setOnClickListener {
            if (currentVerse > 1) {
                currentVerse--
                loadVerse()
            }
        }

        buttonNext.setOnClickListener {
            if (currentVerse < totalVerses) {
                currentVerse++
                loadVerse()
            }
        }

        buttonPlayPause.setOnClickListener {
            togglePlayPause()
        }
    }

    private fun loadVerse() {
        val verseData = getVerseData(chapterNumber, currentVerse)

        // ✅ Update Title
        verseTitle.text = "Sloka $currentVerse"

        textSanskrit.text = verseData.sanskrit
        textEnglish.text = verseData.english
        textSelectedLanguage.text = verseData.selectedLanguage

        buttonPrevious.isEnabled = currentVerse > 1
        buttonNext.isEnabled = currentVerse < totalVerses
    }

    private fun togglePlayPause() {
        isPlaying = !isPlaying

        // ✅ **Animation Effect**
        val fadeAnimation = AlphaAnimation(0.3f, 1.0f)
        fadeAnimation.duration = 300
        buttonPlayPause.startAnimation(fadeAnimation)

        // ✅ **Toggle Play/Pause Image**
        if (isPlaying) {
            buttonPlayPause.setImageResource(R.drawable.ic_pause) // Show Pause
        } else {
            buttonPlayPause.setImageResource(R.drawable.ic_play) // Show Play
        }
    }

    private fun getVerseData(chapter: Int, verse: Int): VerseData {
        val selectedLanguage = getSelectedLanguage() // Fetch from SharedPreferences
        return VerseData(
            sanskrit = "धृतराष्ट्र उवाच | धर्मक्षेत्रे कुरुक्षेत्रे समवेता युयुत्सवः | मामकाः पाण्डवाश्चैव किमकुर्वत सञ्जय ||",
            english = getTranslation(chapter, verse, "english"),
            selectedLanguage = getTranslation(chapter, verse, selectedLanguage)
        )
    }

    private fun getTranslation(chapter: Int, verse: Int, language: String): String {
        val translations = mapOf(
            "hindi" to "धृतराष्ट्र ने कहा: हे संजय, मेरे पुत्रों और पांडवों ने धर्मक्षेत्र कुरुक्षेत्र में क्या किया?",
            "telugu" to "ధృతరాష్ట్రుడు చెప్పెను: ఓ సంజయ, నా కుమారులు మరియు పాండవులు ధర్మక్షేత్రంలో ఏమి చేసారు?",
            "tamil" to "திருதராஷ்டிரன் கூறினான்: ஓ சஞ்சயா, என் மகன்களும் பாண்டவர்களும் குறுக்ஷேத்ரத்தில் என்ன செய்தார்கள்?",
            "kannada" to "ಧೃತರಾಷ್ಟ್ರನು ಹೇಳಿದನು: ಓ ಸಂಜಯ, ನನ್ನ ಮಕ್ಕಳೂ ಪಾಂಡವರೂ ಕುರುಕ್ಷೇತ್ರದಲ್ಲಿ ಏನು ಮಾಡಿದರು?",
            "english" to "Dhritarashtra said: O Sanjaya, what did my sons and the sons of Pandu do at the holy land of Kurukshetra?"
        )
        return translations[language.lowercase()] ?: "Translation not available"
    }

    private fun getSelectedLanguage(): String {
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        return sharedPreferences.getString("language", "english") ?: "english"
    }

    data class VerseData(val sanskrit: String, val english: String, val selectedLanguage: String)
}
