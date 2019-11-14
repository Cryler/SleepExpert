package com.example.sleepexpert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonDiary = findViewById<Button>(R.id.buttonDiary)
        buttonDiary.setOnClickListener{
            startActivity(Intent(this, Diary::class.java))
        }
        buttonAlarm.setOnClickListener{

        }
    }
}
