package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Overview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        val buttonEntries = findViewById<Button>(R.id.buttonEntries)
        buttonEntries.setOnClickListener{
            startActivity(Intent(this, Entries::class.java))
        }

        val buttonProgress = findViewById<Button>(R.id.buttonProgress)
        buttonProgress.setOnClickListener{
            startActivity(Intent(this, Graphs::class.java))
        }

    }
}
