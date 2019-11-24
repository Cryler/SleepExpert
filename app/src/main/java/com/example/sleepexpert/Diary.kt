package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class Diary : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)



        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        val buttonMorningEntry = findViewById<Button>(R.id.buttonMorningEntry)
        buttonMorningEntry.setOnClickListener{
            startActivity(Intent(this, MorningEntry::class.java))
        }

        val buttonEveningEntry = findViewById<Button>(R.id.buttonEveningEntry)
        buttonEveningEntry.setOnClickListener{
            startActivity(Intent(this, EveningEntry::class.java))
        }

    }
}