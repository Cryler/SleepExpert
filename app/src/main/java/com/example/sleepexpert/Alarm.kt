package com.example.sleepexpert

import android.app.*
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*
import java.text.DateFormat
//import android.text.format.DateFormat
import android.os.SystemClock
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat


class Alarm : AppCompatActivity() {
    var alarmTime: TimePicker? = null
    var alarmManager: AlarmManager? = null
    var pIntent: PendingIntent? = null
  //  val picker: TimePicker = findViewById<TimePicker>(R.id.alarmTimePicker)

    var c = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)


        //shows timepicker either in 24 or 12 hour mode depending on sytemsettings
        alarmTimePicker.setIs24HourView(android.text.format.DateFormat.is24HourFormat(applicationContext))


        //retrieve toggle state and text state, update toasts via shared preference
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val tgpref = preferences.getBoolean("tgpref", false)  //default is false
        val timeText = preferences.getString("sharedtext", "")  //default is empty
        if (tgpref) {

            val mTextView = findViewById<TextView>(R.id.alarmTimeText)
            mTextView.text = timeText

            toggleButton.isChecked = true

            Toast.makeText(this, timeText, Toast.LENGTH_SHORT).show()

        } else {

            toggleButton.isChecked = false
            Toast.makeText(this, "Noch kein Wecker gestellt", Toast.LENGTH_SHORT).show()

        }


        alarmTime = findViewById<TimePicker>(R.id.alarmTimePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        toggleButton.setOnClickListener { v -> alarmStart(v) }
    }


    private fun alarmStart(view: View) {

        val checked = toggleButton.isChecked

        if (checked) {

            //shared preferences to save toggle state
            val pref = getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean("tgpref", true) // value to store
            editor.commit()


            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime!!.currentHour)
            calendar.set(Calendar.MINUTE, alarmTime!!.currentMinute)

            c = calendar
            //set TextView with current Alarmtime
            updateTimeText(calendar)


            val intent = Intent(this, AlarmHelper::class.java)
            pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            var pickerTime = calendar.timeInMillis - calendar.timeInMillis % 60000

            if (System.currentTimeMillis() > pickerTime!!) {
                if (Calendar.AM_PM === 0)
                    pickerTime += 1000 * 60 * 60 * 12
                else
                    pickerTime += pickerTime + 1000 * 60 * 60 * 24
            }



            alarmManager!!.set(AlarmManager.RTC_WAKEUP, pickerTime!!, pIntent)

           // scheduleNotification(getNotification("5 second delay"), calendar.timeInMillis.toInt())



            Toast.makeText(
                this,
                "Wecker ist aktiviert",
                Toast.LENGTH_SHORT
            ).show()

            //update the textview to currently set time
            updateTimeText(calendar)


        } else {
            //stop ringtone
            val intent = Intent(this, AlarmHelper::class.java)
            pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (Utility.ringtoneHelper != null) {
                Utility.ringtoneHelper!!.stopRingtone()
            }


                alarmManager!!.cancel(pIntent)

            Toast.makeText(this, "Wecker deaktiviert", Toast.LENGTH_SHORT).show()

            val mTextView = findViewById<TextView>(R.id.alarmTimeText)
            var timeText = "Wecker nicht gestellt"
            mTextView.text = timeText

//shared preferences for toggle save
            val pref = getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean("tgpref", false)
            // value to store
            editor.apply()
        }

    }

    private fun updateTimeText(c: Calendar) {
        val mTextView = findViewById<TextView>(R.id.alarmTimeText)
        var timeText = "Wecker gestellt für: "
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime())

        mTextView.text = timeText

        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("sharedtext", timeText)
        // value to store
        editor.apply()
    }





}