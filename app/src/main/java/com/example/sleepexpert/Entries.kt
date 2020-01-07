package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_entries.*
import kotlinx.android.synthetic.main.activity_evening_entry.*

class Entries : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String

    private lateinit var linearLayoutManager: LinearLayoutManager
//    private lateinit var constraintLayoutManager: LinearLayoutManager


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
        //LinearView.VERTICAL lmao

        //dummy data
        val entries = ArrayList<EntriesItems>()
        val realEntries = ArrayList<EntriesItems>()

        entries.add(EntriesItems("sample Date 1", "sample Hours 1"))
        entries.add(EntriesItems("sample Date 2", "sample Hours 2"))
        entries.add(EntriesItems("sample Date 3", "sample Hours 3"))

        //db query
        val collection = db.collection("users/$user/MorningEntries")
        collection.get()
            .addOnSuccessListener {

                val documents = it.documents

                val descCards = ArrayList<EntriesItems>(documents.size)
                for (i in 0 until documents.size) {
                    val document = documents[i]
                    val date = document[DATE_KEY].toString()
                    val hoursOfSleep = document[HOWLONGSLEEP_KEY].toString()
                    realEntries.add(EntriesItems(date, hoursOfSleep))
                }

        val adapter = EntriesAdapater(entries)

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