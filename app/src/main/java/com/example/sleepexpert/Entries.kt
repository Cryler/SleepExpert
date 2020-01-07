package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Entries : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entries)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().uid.toString()

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

      val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val entries = ArrayList<EntryItem>()

        //db query
        val collection = db.collection("users/$user/MorningEntries")
        collection.get()
            .addOnSuccessListener {
                val documents = it.documents
                for (i in 0 until documents.size) {
                    val document = documents[i]
                    val date = document[DATE_KEY].toString().take(12).trim()
                    val hoursOfSleep = "${document[HOWLONGSLEEP_KEY].toString()} h geschlafen"
                    entries.add(EntryItem(i, date, hoursOfSleep))
                }

        val adapter = EntriesAdapter(entries)

        recyclerView.adapter = adapter
    }}

    companion object {
        private const val DATE_KEY = "date"
        private const val SLEEPQUALITY_KEY = "sleep quality"
        private const val RESTED_KEY = "feeling rested"
        private const val TIREDNESS_KEY = "tiredness when going to bed"
        private const val BEDTIME_KEY = "bedtime"
        private const val DOINGINBED_KEY = "doing before sleep"
        private const val LIGHTSOUT_KEY = "lights out"
        private const val FALLASLEEP_KEY = "estimated fall asleep time"
        private const val WAKEUP_KEY = "woke up"
        private const val GOTUP_KEY = "got up"
        private const val HOWLONGSLEEP_KEY = "sleep time"
    }
}