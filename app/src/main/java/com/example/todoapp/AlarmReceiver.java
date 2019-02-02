package com.example.todoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent= new Intent(context, AddToDoActivity.class);

        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
       stackBuilder.addParentStack(AddToDoActivity.class);
       stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent= stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);

        Notification notification= builder.setContentTitle("To Do Notification")
                .setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);


    }
}
