package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Infos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val buttonKnowledge = findViewById<Button>(R.id.buttonKnowledge)
        buttonKnowledge.setOnClickListener{
            startActivity(Intent(this, Knowledge::class.java))
        }

        val buttonTips = findViewById<Button>(R.id.buttonTips)
        buttonTips.setOnClickListener{
            startActivity(Intent(this, Tips::class.java))
        }

    }
}
