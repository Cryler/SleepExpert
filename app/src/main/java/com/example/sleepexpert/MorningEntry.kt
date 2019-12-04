package com.example.sleepexpert

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_morning_entry.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class MorningEntry : AppCompatActivity() {
    companion object {
        const val DATE_KEY = "date"
        const val SLEEPQUALITY_KEY = "sleep quality"
        const val GOINGTOBED_KEY = "Went to bed"
        const val DOINGINBED_KEY = "Doing before sleep"
        const val HOWLONGSLEEP_KEY = "Sleep time"
    }

    // Access a Cloud Firestore instance from your Activity
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_entry)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().uid.toString()


        val buttonBack = findViewById<Button>(R.id.buttonBackToDiary)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, Diary::class.java))
        }

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        val formattedDate = formatter.format(date)

        val labelDate = findViewById<TextView>(R.id.labelDate)
        labelDate.setText(formattedDate)

        val seekbarSleepQuality = findViewById<SeekBar>(R.id.seekbarSleepQualityMorning)
        seekbarSleepQuality?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val i = progress + 1
                labelSleepQualityMorning.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })


        val timePickerBedTime = findViewById<TimePicker>(R.id.timePickerBedTimeMorning)
        timePickerBedTime.setIs24HourView(true)
        val timePickerSleepTime = findViewById<TimePicker>(R.id.timePickerSleepTimeMorning)
        timePickerSleepTime.setIs24HourView(true)


        val buttonSave = findViewById<Button>(R.id.buttonSaveMorningEntry)
        buttonSave.setOnClickListener{
            saveData()
        }

    }

    /*
    Current date and time
     */
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun saveData() {
        val date = findViewById<TextView>(R.id.labelDate).text
        val seekbarSleepQuality = findViewById<SeekBar>(R.id.seekbarSleepQualityMorning)
        val timepickerBedTime = findViewById<TimePicker>(R.id.timePickerBedTimeMorning)
        val bedTime = timepickerBedTime.hour.toString() + ":" + timePickerBedTimeMorning.minute.toString()
        val textareaDoingInBed = findViewById<EditText>(R.id.textareaDoingInBedMorning).text
        val timepickerSleepTime = findViewById<TimePicker>(R.id.timePickerSleepTimeMorning)
        val sleepTime = timepickerSleepTime.hour.toString() + ":" + timePickerSleepTimeMorning.minute.toString()
        var value = seekbarSleepQuality.progress
        value++

        if (bedTime.isEmpty()) {
            Toast.makeText(this, "Bitte ausfüllen wann Sie zu Bett gegangen sind", Toast.LENGTH_SHORT).show()
            return
        }
        if (sleepTime.isEmpty()) {
            Toast.makeText(this, "Bitte Schlafenszeit ausfüllen", Toast.LENGTH_SHORT).show()
            return
        }

        val dataToSave: HashMap<String, String> = HashMap<String, String>()
        dataToSave.put(SLEEPQUALITY_KEY, value.toString())
        dataToSave.put(DATE_KEY, date.toString())
        dataToSave.put(GOINGTOBED_KEY, bedTime)
        dataToSave.put(HOWLONGSLEEP_KEY, sleepTime)
        dataToSave.put(DOINGINBED_KEY, textareaDoingInBed.toString())
        val userCollRef = db.document("users/$user/MorningEntries/$date")
        userCollRef.set(dataToSave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("DataStore", "Data successfully saved to firebase database")
                    Toast.makeText(
                        baseContext, "Morgeneintrag gespeichert",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("DataStore", "Error trying to save", task.exception)
                    Toast.makeText(
                        baseContext, "Fehler beim Speichern des Morgeneintrages",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}