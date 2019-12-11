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
import kotlinx.android.synthetic.main.activity_evening_entry.*
import java.text.SimpleDateFormat
import java.util.*

class EveningEntry : AppCompatActivity() {
    companion object {
        private const val DATE_KEY = "date"
        private const val SLEEPINESS_KEY = "sleepiness"
        private const val CONCENTRATION_KEY = "concentration"
        private const val MOOD_KEY = "mood"
        private const val DAYTIMESLEEP_KEY = "daytime sleep"
        private const val STIMULANTS_KEY = "stimulants"
        private const val RELAXATION_KEY = "physical relaxation"

    }

    // Access a Cloud Firestore instance from your Activity
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evening_entry)

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

        val labelDate = findViewById<TextView>(R.id.labelDateEvening)
        labelDate.setText(formattedDate)

        // shows the right value in the label of seekbar, when seekbar gets changed
        val seekbarSleepiness = findViewById<SeekBar>(R.id.seekbarDaytimeSleepinessEvening)
        seekbarSleepiness?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val i = progress + 1
                labelDaytimeSleepinessEvening.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })
        val seekbarConcentration = findViewById<SeekBar>(R.id.seekbarConcentrationEvening)
        seekbarConcentration?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val i = progress + 1
                labelConcentrationEvening.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })
        val seekbarMood = findViewById<SeekBar>(R.id.seekbarMoodEvening)
        seekbarMood?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val i = progress + 1
                labelMoodEvening.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })
        val seekbarRelaxation = findViewById<SeekBar>(R.id.seekbarPhysicalRelaxationEvening)
        seekbarRelaxation?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val i = progress + 1
                labelPhysicalRelaxation.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
            }
        })

        // save entry
        val buttonSave = findViewById<Button>(R.id.buttonSaveEveningEntry)
        buttonSave.setOnClickListener {
            saveData()
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        val date = findViewById<TextView>(R.id.labelDateEvening).text
        val seekbarSleepiness = findViewById<SeekBar>(R.id.seekbarDaytimeSleepinessEvening)
        var sleepinessValue = seekbarSleepiness.progress
        sleepinessValue++
        val seekbarConcentration = findViewById<SeekBar>(R.id.seekbarConcentrationEvening)
        var concentrationValue = seekbarConcentration.progress
        concentrationValue++
        val seekbarMood = findViewById<SeekBar>(R.id.seekbarMoodEvening)
        var moodValue = seekbarMood.progress
        moodValue++
        val seekbarRelaxation = findViewById<SeekBar>(R.id.seekbarPhysicalRelaxationEvening)
        var relaxationValue = seekbarRelaxation.progress
        relaxationValue++
        val textareaDaytimeSleep = findViewById<EditText>(R.id.textareaDaytimeSleep).text
        val textareaStimulants = findViewById<EditText>(R.id.textareaStimulants).text


        // Validators
        var message = ""
        when {
            textareaDaytimeSleep.isEmpty() -> message =
                "Bitte ausfüllen ob Sie Tagesschläfe hatten."
            textareaStimulants.isEmpty() -> message =
                "Bitte ausfüllen was ob Sie Genussmittel zu sich genommen haben."
        }
        if (message.isNotEmpty()) {
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // save data to firebase db
        val dataToSave: HashMap<String, String> = HashMap()
        dataToSave.put(DATE_KEY, date.toString())
        dataToSave.put(SLEEPINESS_KEY, sleepinessValue.toString())
        dataToSave.put(CONCENTRATION_KEY, concentrationValue.toString())
        dataToSave.put(MOOD_KEY, moodValue.toString())
        dataToSave.put(RELAXATION_KEY, relaxationValue.toString())
        dataToSave.put(DAYTIMESLEEP_KEY, textareaDaytimeSleep.toString())
        dataToSave.put(STIMULANTS_KEY, textareaStimulants.toString())

        val userCollRef = db.document("users/$user/EveningEntries/$date")
        userCollRef.set(dataToSave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("DataStore", "Data successfully saved to firebase database")
                    Toast.makeText(
                        baseContext, "Abendeintrag gespeichert",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("DataStore", "Error trying to save", task.exception)
                    Toast.makeText(
                        baseContext, "Fehler beim Speichern des Abendeintrages",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}