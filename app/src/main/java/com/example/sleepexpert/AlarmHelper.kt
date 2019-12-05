package com.example.sleepexpert

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed

internal interface RingtoneHelper {
    fun stopRingtone()
}

internal object Utility {

    var ringtoneHelper: RingtoneHelper? = null


}


class AlarmHelper : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Hey Fucker get up!", Toast.LENGTH_LONG).show()
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
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

