package com.rz.movieapp.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.rz.movieapp.R;
import com.rz.movieapp.ui.activities.home.HomeActivity;

import java.util.Calendar;

public class DailyReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TITLE = "title";

    public static final String CH_ID ="channelID_DailyReminder";
    public static final String CH_NAME ="channelNAME_DailyReminder";
    public static final int NOTIF_ID = 25;

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotifications(context, intent);
    }

    private void showNotifications(Context context ,Intent alarmIntent){
        NotificationManager notifMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIF_ID, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String msg = alarmIntent.getStringExtra(EXTRA_MESSAGE);
        String title = alarmIntent.getStringExtra(EXTRA_TITLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, msg)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000})
                .setSound(ringtone)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CH_ID, CH_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            channel.setVibrationPattern(new long[]{1000, 1000});

            builder.setChannelId(CH_ID);
            if (notifMgr != null) {
                notifMgr.createNotificationChannel(channel);
            }
        }

        if(notifMgr != null){
            notifMgr.notify(NOTIF_ID, builder.build());
        }
    }

    public void cancelDailyReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setDailyReminder(Context context, String title, String time, String message){
//        cancelDailyReminder(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TITLE, title);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }
}
