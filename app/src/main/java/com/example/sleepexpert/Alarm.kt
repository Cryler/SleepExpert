package com.example.sleepexpert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*
import java.util.*
import java.text.DateFormat


class Alarm : AppCompatActivity() {
    var alarmTime: TimePicker? = null
    var alarmManager: AlarmManager? = null
    var pIntent: PendingIntent? = null


    var c = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)





        //retrieve toggle state
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val tgpref = preferences.getBoolean("tgpref", false)  //default is true

        if (tgpref) {



            toggleButton.isChecked = true

            Toast.makeText(this, "Wecker ist nach wie vor gestellt für " + DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()), Toast.LENGTH_SHORT).show()
           updateTimeText(c)
        } else {

            toggleButton.isChecked= false
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


            Toast.makeText(
                this,
                "Wecker ist aktiviert",
                Toast.LENGTH_SHORT
            ).show()

            //update the textview to currently set time
            updateTimeText(calendar)


        } else {
            //stop ringtone
            if (Utility.ringtoneHelper != null) {
                Utility.ringtoneHelper!!.stopRingtone()
            }

            if (pIntent != null){
            alarmManager!!.cancel(pIntent)}
            Toast.makeText(this, "Alarm ist deaktiviert", Toast.LENGTH_SHORT).show()

            val mTextView = findViewById<TextView>(R.id.alarmTimeText)
            var timeText = "Alarm not set"
            mTextView.text = timeText
//shared preferences for toggle save

            val pref = getPreferences(Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean("tgpref", false) // value to store
            editor.apply()
        }

    }

    private fun updateTimeText(c: Calendar) {
        val mTextView = findViewById<TextView>(R.id.alarmTimeText)
        var timeText = "Alarm set for: "
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime())

        mTextView.text = timeText
    }


}