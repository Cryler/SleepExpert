package com.example.sleepexpert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class  MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val buttonDiary = findViewById<Button>(R.id.buttonDiary)
        buttonDiary.setOnClickListener{
            startActivity(Intent(this, Diary::class.java))
        }
        val buttonInfos = findViewById<Button>(R.id.buttonInfos)
        buttonInfos.setOnClickListener{
            startActivity(Intent(this, Infos::class.java))
        }
        val buttonOverview = findViewById<Button>(R.id.buttonOverview)
        buttonOverview.setOnClickListener{
            startActivity(Intent(this, Overview::class.java))
        }
        val buttonAlarm = findViewById<Button>(R.id.buttonAlarm)
        buttonAlarm.setOnClickListener{
            startActivity(Intent(this, Alarm::class.java))
        }

        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        buttonLogout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, Login::class.java))
        }
    }
}
