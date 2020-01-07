package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class Knowledge : AppCompatActivity() {

    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge)

        val buttonBack = findViewById<Button>(R.id.buttonBackToInfo)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, Infos::class.java))
        }

        val title1 = "Was passiert im Schlaf?"
        val title2 = "Warum ist ein guter Schlaf wichtig?"
        val title3 = "Was geschieht beim Träumen?"
        val title4 = "Wie wird man Albträume los?"
        val title5 = "Wer schläft wie – und wie lang?"
        val title6 = "Fragwürdige Schlaflosigkeit"
        val title7 = "Im Pyjama oder nackt?"
        val title8 = "Wofür ist das Gähnen gut?"
        val title9 = "Was ist Schlafwandeln?"
        val title10 = "Wieso knirschen Menschen im Schlaf mit den Zähnen?"

        val myDataset = arrayOf(title1, title2, title3, title4, title5, title6, title7, title8, title9, title10)

        listView = findViewById<ListView>(R.id.recipe_list_view)
        val mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myDataset)
        listView.adapter = mAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ListItemKnowledge::class.java)
            intent.putExtra("key", position)
            startActivity(intent)

        }

    }
}