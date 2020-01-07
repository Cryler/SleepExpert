package com.example.sleepexpert

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_morning_entry.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class MorningEntryEdit : AppCompatActivity() {
    companion object {
        const val DOCUMENT_ID = "documentId"
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

    // Access a Cloud Firestore instance from your Activity
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: String
    private lateinit var document: DocumentSnapshot


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_entry)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().uid.toString()

        // get document id from extras
        val documentIdExtra = intent.getStringExtra(DOCUMENT_ID)?:""
        if (documentIdExtra.isEmpty()) {
            finish()
        }

        // get document from database
        val collection = db.collection("users/$user/MorningEntries")
        collection.get()
            .addOnSuccessListener {
                document = it.documents[documentIdExtra.toInt()]
                val date = document[DATE_KEY].toString()
                val sleepQuality = document[SLEEPQUALITY_KEY].toString().toInt()
                val feelingRested = document[RESTED_KEY].toString().toInt()
                val tiredness = document[TIREDNESS_KEY].toString().toInt()
                val bedtime = document[BEDTIME_KEY].toString()
                val doingInBed = document[DOINGINBED_KEY ].toString()
                val lightsOut = document[LIGHTSOUT_KEY ].toString()
                val fallAsleep = document[FALLASLEEP_KEY ].toString()
                val wakeUp = document[WAKEUP_KEY ].toString()
                val gotUp = document[GOTUP_KEY ].toString()
                val howLongSleep = document[HOWLONGSLEEP_KEY ].toString()


                val labelDate = findViewById<TextView>(R.id.labelDateMorning)
                labelDate.text = date

                // shows the right value in the label of seekbar, when seekbar gets changed
                val seekbarSleepQuality = findViewById<SeekBar>(R.id.seekbarSleepQualityMorning)
                seekbarSleepQuality.progress = sleepQuality - 1
                labelSleepQualityMorning.text = sleepQuality.toString()
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
                val seekbarRested = findViewById<SeekBar>(R.id.seekbarFeelingRestedMorning)
                seekbarRested.progress = feelingRested - 1
                labelFeelingRestedMorning.text = feelingRested.toString()
                seekbarRested?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        val i = progress + 1
                        labelFeelingRestedMorning.text = i.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // Write code to perform some action when touch is started.
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // Write code to perform some action when touch is stopped.
                    }
                })
                val seekbarTiredness = findViewById<SeekBar>(R.id.seekbarTirednessGoingToBedMorning)
                seekbarTiredness.progress = tiredness - 1
                labelFeelingTirednessGoingToBedMorning.text = tiredness.toString()
                seekbarTiredness?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        val i = progress + 1
                        labelFeelingTirednessGoingToBedMorning.text = i.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // Write code to perform some action when touch is started.
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // Write code to perform some action when touch is stopped.
                    }
                })

                // sets onClickListeners on edittexts
                val bedtimeLabel= findViewById<EditText>(R.id.editTextBedTime)
                bedtimeLabel.setText(bedtime)
                timePickerDialog(bedtimeLabel)
                val doingInBedTextArea = findViewById<EditText>(R.id.textareaDoingInBedMorning)
                doingInBedTextArea.setText(doingInBed)
                val lightsOutLabel= findViewById<EditText>(R.id.editTextLightsOut)
                lightsOutLabel.setText(lightsOut)
                timePickerDialog(lightsOutLabel)
                val fallAsleepLabel= findViewById<EditText>(R.id.editTextFallAsleep)
                fallAsleepLabel.setText(fallAsleep)
                timePickerDialogSpinner(fallAsleepLabel)
                val wakeUpLabel= findViewById<EditText>(R.id.editTextWakeUp)
                wakeUpLabel.setText(wakeUp)
                timePickerDialog(wakeUpLabel)
                val gotUpLabel= findViewById<EditText>(R.id.editTextGotUp)
                gotUpLabel.setText(gotUp)
                timePickerDialog(gotUpLabel)
                val sleepTimeLabel= findViewById<EditText>(R.id.editTextSleepTime)
                sleepTimeLabel.setText(howLongSleep)
                timePickerDialogSpinner(sleepTimeLabel)
            }

        // go back to entry menu
        val buttonBack = findViewById<Button>(R.id.buttonBackToDiary)
        buttonBack.setOnClickListener {
            finish()
        }



        // save entry
        val buttonSave = findViewById<Button>(R.id.buttonSaveMorningEntry)
        buttonSave.setOnClickListener {
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


    // connects time picker dialogs to edittext fields and changes the value to the chosen time
    private fun timePickerDialog(text: EditText) {
        text.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                text.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
    }

    // connects time picker dialogs in spinner mode to edittext fields and changes the value to the chosen time
    private fun timePickerDialogSpinner(text: EditText) {
        text.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                text.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(this, R.style.CustomDatePickerDialog, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun saveData() {
        val date = findViewById<TextView>(R.id.labelDateMorning).text
        val seekbarSleepQuality = findViewById<SeekBar>(R.id.seekbarSleepQualityMorning)
        var sleepQualValue = seekbarSleepQuality.progress
        sleepQualValue++
        val seekbarRested = findViewById<SeekBar>(R.id.seekbarFeelingRestedMorning)
        var restedValue = seekbarRested.progress
        restedValue++
        val seekbarTiredness = findViewById<SeekBar>(R.id.seekbarTirednessGoingToBedMorning)
        var tirednessValue = seekbarTiredness.progress
        tirednessValue++
        val bedTime = findViewById<EditText>(R.id.editTextBedTime).text.toString()
        val textareaDoingInBed = findViewById<EditText>(R.id.textareaDoingInBedMorning).text
        val lightsOut = findViewById<EditText>(R.id.editTextLightsOut).text.toString()
        val fallAsleep = findViewById<EditText>(R.id.editTextFallAsleep).text.toString()
        val wakeUp = findViewById<EditText>(R.id.editTextWakeUp).text.toString()
        val gotUp = findViewById<EditText>(R.id.editTextGotUp).text.toString()
        val sleepTime = findViewById<EditText>(R.id.editTextSleepTime).text.toString()

        // Validators
        var message = ""
        when {
            bedTime.isEmpty() -> message = "Bitte ausfüllen wann Sie zu Bett gegangen sind."
            textareaDoingInBed.isEmpty() -> message ="Bitte ausfüllen was Sie noch im Bett gemacht haben."
            lightsOut.isEmpty() -> message = "Bitte ausfüllen wann Sie das Licht gelöscht haben."
            fallAsleep.isEmpty() -> message = "Bitte die geschätzte Einschlafdauer ausfüllen."
            wakeUp.isEmpty() -> message = "Bitte ausfüllen wann Sie morgens aufgewacht sind."
            gotUp.isEmpty() -> message = "Bitte ausfüllen wann Sie morgens aufgestanden sind."
            sleepTime.isEmpty() -> message = "Bitte ausfüllen wie lange Sie geschlafen haben."
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
        dataToSave.put(SLEEPQUALITY_KEY, sleepQualValue.toString())
        dataToSave.put(RESTED_KEY, restedValue.toString())
        dataToSave.put(TIREDNESS_KEY, tirednessValue.toString())
        dataToSave.put(BEDTIME_KEY, bedTime)
        dataToSave.put(DOINGINBED_KEY, textareaDoingInBed.toString())
        dataToSave.put(LIGHTSOUT_KEY, lightsOut)
        dataToSave.put(FALLASLEEP_KEY, fallAsleep)
        dataToSave.put(WAKEUP_KEY, wakeUp)
        dataToSave.put(GOTUP_KEY, gotUp)
        dataToSave.put(HOWLONGSLEEP_KEY, sleepTime)


        db.document("users/$user/MorningEntries/$date").update(dataToSave.toMap())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("DataStore", "Data update successfully saved to firebase database")
                    Toast.makeText(
                        baseContext, "Morgeneintrag aktualisiert",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, Entries::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("DataStore", "Error trying to save update", task.exception)
                    Toast.makeText(
                        baseContext, "Fehler beim Speichern des Morgeneintrag-Updates",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}