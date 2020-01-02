package com.example.sleepexpert

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.HandlerCompat.postDelayed
import android.R
import android.app.*
import androidx.core.app.NotificationCompat


internal interface RingtoneHelper {
    fun stopRingtone()
}

internal object Utility {

    var ringtoneHelper: RingtoneHelper? = null


}





class AlarmHelper : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {



        Toast.makeText(context, "Wake up! Wake up!", Toast.LENGTH_LONG).show()
//use ringtone
        val defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
//use default tone (beep beep)
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
//use ringtone
        val ringtone = RingtoneManager.getRingtone(context, defaultRingtoneUri)
       //use default tone (beep beep)
       // val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone!!.play()

        Utility.ringtoneHelper = object : RingtoneHelper {
            override fun stopRingtone() {
                if (ringtone.isPlaying) {
                    ringtone.stop()
                }
            }
        }
    }
}


/*
       //stop alarm after 10 seconds


        val handler = Handler()
        handler.postDelayed(Runnable { ringtone.stop() }, 1000 * 10)
        }


    }
*/

