package com.wgu.myapplication.utilities;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.wgu.myapplication.MainActivity;
import com.wgu.myapplication.R;

public class MyReceiver extends BroadcastReceiver {
    private final String channel_id = "Travelers_Compendium_Alerts";
    private static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String alertMessage = intent.getStringExtra("alertMessage");
//        String alarmType = intent.getStringExtra("alarmType");
//
//        UnlockableChecks unlock = new UnlockableChecks(context);

//        if ("start".equals(alarmType)) {
//            unlock.dataCheck(title, context, true);
//        } else if ("end".equals(alarmType)) {
//            unlock.dataCheck(title, context, false);
//        }

        createNotificationChannel(context);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("title", title);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(alertMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);
    }


    private void createNotificationChannel(Context context) {
        CharSequence name = "Journey Alerts";
        String description = "Alerts for Journey start and end dates";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
