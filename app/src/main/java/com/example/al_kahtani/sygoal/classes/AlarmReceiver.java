package com.example.al_kahtani.sygoal.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.al_kahtani.sygoal.BottomNavigationViewActivity;
import com.example.al_kahtani.sygoal.R;

/**
 * Created by sara on 2017/12/28.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        int random = intent.getIntExtra("random", 0);
        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("todo");
        setAlarm(context, random, notificationId, message);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setAlarm(Context context, int random, int notificationId, String message) {

        // Get id & message from intent.

        // String repeat = intent.getStringExtra("repeating");

        // When notification is tapped, call MainActivity.
        Intent mainIntent = new Intent(context, BottomNavigationViewActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, random, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
// PendingIntent notificIntent = PendingIntent.getActivity(context,java.util.Random.nextInt(100000), i ,PendingIntent.FLAG_CANCEL_CURRENT);//PendingIntent.FLAG_UPDATE_CURRENT
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Prepare notification.
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_launcher1)

                .setContentTitle("SYGoal")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        // Notify
        myNotificationManager.notify(notificationId, builder.build());


    }


}
