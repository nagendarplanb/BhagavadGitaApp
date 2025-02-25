package com.example.bhagavadgitaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize back button
        val backButton = view.findViewById<ImageButton>(R.id.backButton)

        // Set back button click listener
        backButton.setOnClickListener {
            val intent = Intent(activity, LanguageSelectionActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Finish Home screen so back press doesn't return to it
        }

        // Set click listeners for chapter buttons
        for (i in 1..18) {
            val buttonId = resources.getIdentifier("button_chapter_$i", "id", requireActivity().packageName)
            val chapterButton = view.findViewById<Button>(buttonId)
            chapterButton?.setOnClickListener { openChapter(i) }
        }

        return view
    }

    private fun openChapter(chapterNumber: Int) {
        val intent = Intent(activity, VerseActivity::class.java)
        intent.putExtra("CHAPTER_NUMBER", chapterNumber)
        startActivity(intent)
        Log.d("HomeFragment", "Opening Chapter $chapterNumber in com.example.bhagavadgitaapp.VerseActivity")
    }
}
