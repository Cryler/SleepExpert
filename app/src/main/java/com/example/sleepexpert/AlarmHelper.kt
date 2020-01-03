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
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import android.app.PendingIntent




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
        issueNotification(context)

        //issue second notification with delay of 2h (7200000 ms)
        val handler = Handler()
        handler.postDelayed({
            // createNotification(SmsMessage.createFromPdu((byte[])smsExtra[0]), context);
            issueNotification2(context)
        }, 7200000)
        Utility.ringtoneHelper = object : RingtoneHelper {
            override fun stopRingtone() {
                if (ringtone.isPlaying) {
                    ringtone.stop()
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id: String, name: String, importance: Int, context: Context) {
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true) // set false to disable badges, Oreo exclusive
        channel.setSound(null, null)
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    private fun issueNotification(context: Context) {

        // make the channel. The method has been discussed before.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_3", "Example channel", NotificationManager.IMPORTANCE_HIGH, context)

        }
        // the check ensures that the channel will only be made
        // if the device is running Android 8+

        val notification = NotificationCompat.Builder(context, "CHANNEL_3")

        val intent = Intent(context, Alarm::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        notification.setContentIntent(pendingIntent)

        notification
            .setSmallIcon(R.mipmap.sym_def_app_icon) // can use any other icon
            .setContentTitle("Aufstehen bitte!")
            .setContentText("Vergessen Sie nicht, ihr Tagebuch auszufüllen")
            .setNumber(3) // this shows a number in the notification dots
            .setSound(null)


        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

       notificationManager.notify(1, notification.build())



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel2(id: String, name: String, importance: Int, context: Context) {
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true) // set false to disable badges, Oreo exclusive

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    private fun issueNotification2(context: Context) {

        // make the channel. The method has been discussed before.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel2("CHANNEL_4", "Example channel4", NotificationManager.IMPORTANCE_HIGH, context)

        }

        val notification2 = NotificationCompat.Builder(context, "CHANNEL_4")

        val intent2 = Intent(context, Diary::class.java)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_ONE_SHOT)

        notification2.setContentIntent(pendingIntent2)


        notification2
            .setSmallIcon(R.mipmap.sym_def_app_icon) // can use any other icon
            .setContentTitle("Eintrag schon erfasst?")
            .setContentText("Klicken Sie hier, falls nicht!")
            .setNumber(3)
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

      //  notificationManager.notify(1, notification.build())




            notificationManager.notify(2, notification2.build())



    }


}


/*
       //stop alarm after 10 seconds


        val handler = Handler()
        handler.postDelayed(Runnable { ringtone.stop() }, 1000 * 10)
        }


    }
*/

