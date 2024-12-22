//package com.wgu.myapplication.utilities;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.wgu.myapplication.database.TravelersCompendiumDatabase;
//import com.wgu.myapplication.models.JourneyModel;
//
//public class Notifications {
//
//    private Context context;
//
//    public Notifications(Context context) {
//        this.context = context;
//    }
//
//    public void scheduleJourneyAlarm(Context context, long triggerAtMillis, String journeyTitle, String alarmType) {
//        TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(context);
//        JourneyModel journey = db.journeyDao().getJourneyByName(journeyTitle);
//
//        Intent intent = new Intent(context, MyReceiver.class);
//        intent.setAction("com.wgu.myapplication.index_buttons.START_ALARM");
//        intent.putExtra("title", journeyTitle);
//        if ("start".equals(alarmType)) {
//            intent.putExtra("alertMessage", "Your journey is starting!");
//            intent.putExtra("alarmType", "start");
//        } else if ("end".equals(alarmType)) {
//            intent.putExtra("alertMessage", "Your journey is ending!");
//            intent.putExtra("alarmType", "end");
//        }
//        int uniqueId = journey.getId() + ("end".equals(alarmType) ? 1 : 0);
//        Log.d("Notifications", "Scheduling alarm with ID: " + uniqueId + " my journey id " + journey.getId());
//        Log.d("alarm time", "alarm type : " +alarmType + "alarm trigger time: " + triggerAtMillis);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueId, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//    }
//
//
//
//    public void scheduleNotification(Context context, long triggerAtMillis, String notificationTitle, String notificationMessage, int notificationId) {
//        Intent intent = new Intent(context, MyReceiver.class);
//        intent.setAction("com.wgu.myapplication.notifications.PUSH_NOTIFICATION");
//        intent.putExtra("title", notificationTitle);
//        intent.putExtra("alertMessage", notificationMessage);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context,
//                notificationId,
//                intent,
//                PendingIntent.FLAG_IMMUTABLE
//        );
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        if (alarmManager != null) {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//        }
//    }
//
//
//    public void cancelAlarm(Context context, int journeyId, String alarmType) {
//        Intent intent = new Intent(context, MyReceiver.class);
//        intent.setAction("com.wgu.myapplication.index_buttons.START_ALARM");
//
//        int uniqueId = journeyId + ("end".equals(alarmType) ? 1 : 0);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                context,
//                uniqueId,
//                intent,
//                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//        Log.d("Notifications", "Canceling alarm with ID: " + uniqueId + " my journey id " + journeyId);
//
//    }
//
//
//}
