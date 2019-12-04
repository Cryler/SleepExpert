package com.example.sleepexpert

import android.app.AlarmManager
import android.app.PendingIntent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alarm.*

import java.util.*

class Alarm : AppCompatActivity() {
    var alarmTime: TimePicker? = null
    var alarmManager: AlarmManager? = null
    var pIntent: PendingIntent? = null

    //get current time and format (unused)
    val date = Calendar.getInstance().time


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        alarmTime = findViewById<TimePicker>(R.id.alarmTimePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

       /* //testing Text-View
        val text: TextView = findViewById(R.id.tester) as TextView
*/

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
/*
        //depreceated button
        val buttonSave = findViewById<Button>(R.id.buttonSaveAlarm)
        buttonSave.setOnClickListener {
            //testing Text-View
            alarmManager!!.cancel(pIntent)
            text.text = alarmTime.toString()
        }
*/

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun onToggleClicked(view: View) {
        alarmStart(view)
    }

    private fun alarmStart(view: View) {
        if ((view as ToggleButton).isChecked) {
            /*textchecker
            val text: TextView = findViewById(R.id.tester) as TextView
            text.text = alarmTime.toString()

             */
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime!!.currentHour)
            calendar.set(Calendar.MINUTE, alarmTime!!.currentMinute)

            val intent = Intent(this, AlarmHelper::class.java)
            pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            var pickerTime = calendar.timeInMillis - calendar.timeInMillis % 60000

            if (System.currentTimeMillis() > pickerTime) {
                if (Calendar.AM_PM === 0)
                    pickerTime += 1000 * 60 * 60 * 12
                else
                    pickerTime += pickerTime + 1000 * 60 * 60 * 24
            }

            alarmManager!!.set(AlarmManager.RTC_WAKEUP, pickerTime, pIntent)
            Toast.makeText(
                this,
                "systime:" + System.currentTimeMillis().toString() + "pickertime:" + pickerTime.toString() + "Wecker ist aktiviert",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            alarmManager!!.cancel(pIntent)
            Toast.makeText(this, "Alarm ist deaktiviert", Toast.LENGTH_SHORT).show()

        }
    }


}
