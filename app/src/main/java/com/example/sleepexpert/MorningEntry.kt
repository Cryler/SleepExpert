package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MorningEntry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_entry)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}