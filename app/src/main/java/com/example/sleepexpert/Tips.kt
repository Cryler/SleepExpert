package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter

import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class Tips : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        val buttonBack = findViewById<Button>(R.id.buttonBackToInfo)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, Infos::class.java))
        }
        val title1 = "Rituale schaffen"
        val title2 = "Probleme aus dem Bett verbannen"
        val title3 = "Weg mit den Schäfchen!"
        val title4 = "Leise Töne für entspanntes Einschlafen"
        val title5 = "In den Schlaf atmen"
        val title6 = "Ein wenig Konzentration"
        val title7 = "Schalten Sie einfach mal ab!"
        val title8 = "Cool bleiben, wenn die Hormone verrücktspielen"

        val myDataset = arrayOf(title1, title2, title3, title4, title5, title6, title7, title8)

        listView = findViewById<ListView>(R.id.recipe_list_view)
        val mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myDataset)
        listView.adapter = mAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ListItemTips::class.java)
            intent.putExtra("key", position)
            startActivity(intent)

        }


    }

}
