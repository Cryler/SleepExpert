package com.example.sleepexpert

import android.content.Intent
import android.graphics.Color.WHITE
import android.graphics.Color.rgb
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class Graphs : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphs)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().uid.toString()

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

//        Graph
        val graph = findViewById<GraphView>(R.id.graph)
        val viewport = graph.viewport
        viewport.isScalable = true
        viewport.isScrollable = true

//        Graph X Label formatting
        viewport.isXAxisBoundsManual = true
        viewport.setMinX(0.0)
        viewport.setMaxX(10.0)


//        Graph Y Label formatting
        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) { // show normal x values
                    super.formatLabel(value, isValueX)
                } else { // show currency for y values
                    super.formatLabel(value, isValueX) + " h"
                }
            }
        }

//        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
//
//        val documentKey = when (radioGroup.checkedRadioButtonId) {
//            R.id.radioStunden -> HOWLONGSLEEP_KEY
//            R.id.radioAufgewacht -> WAKEUP_KEY
//            R.id.radioEingeschlafen -> FALLASLEEP_KEY
//            else -> HOWLONGSLEEP_KEY
//        }

        // temporary solution until radiobuttons are fixed
        val documentKey = HOWLONGSLEEP_KEY

        val collection = db.collection("users/$user/MorningEntries")
        collection.get()
            .addOnSuccessListener {

                val documents = it.documents

                val allTimesVar = arrayOfNulls<DataPoint>(documents.size)
                for (i in 0 until documents.size) {
                    val document = documents[i]
                    val timeString = document[documentKey].toString()
                    val timeStringParts = timeString.split(":")
                    val timeHour = timeStringParts[0].toDouble()
                    val timeMinutes = timeStringParts[1].toDouble()
                    val timeMinutesInDec = timeMinutes / 60
                    val timeInDec = timeHour + timeMinutesInDec
                    allTimesVar[i] = (DataPoint(i.toDouble(), timeInDec))
                }

                val series = LineGraphSeries(allTimesVar)

                series.isDrawBackground = true
                series.thickness = 10
                graph.addSeries(series)
            }
            .addOnFailureListener {
                // TODO exception handling
            }


    }

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