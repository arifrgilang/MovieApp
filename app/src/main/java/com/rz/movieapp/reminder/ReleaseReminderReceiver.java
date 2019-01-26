package com.rz.movieapp.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.data.model.MovieResponse;
import com.rz.movieapp.ui.activities.detail.DetailMovieActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;

public class ReleaseReminderReceiver extends BroadcastReceiver {

    private static final String CH_ID ="CH_02_release";
    private static final String CH_NAME ="Release Reminder Channel";
    private static final int NOTIF_ID = 26;

    MovieDBClient mService;
    ArrayList<MovieObject> mList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        getTodayMovies(context);
    }

    private void arrangeNotifications(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);
        int notifId;
        for (int i = 0; i < mList.size(); i++) {
            MovieObject item = mList.get(i);
            notifId = Integer.parseInt(item.getId());

            if (item.getRelease_date().equals(today)) {
                showNotifications(context, item, notifId);
                Log.d("SHOW NOTIF RELEASE", item.getOriginal_title());
            }
        }
    }

    private void showNotifications(Context context, MovieObject movieObject, int notifId) {
        NotificationManager notifMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieObject.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String title = context.getString(R.string.release_title);
        String msg = movieObject.getOriginal_title();

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
            notifMgr.notify(notifId, builder.build());
        }
    }

    private void getTodayMovies(final Context context) {
        mService = SupportRetrofit.provideMovieDBClient();
        Log.d("TODAY MOVIES", "gettoday movies");
        Disposable disposable = mService.getUpcomingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieResponse>() {
                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        mList = movieResponse.getResults();
                        Log.d("TODAY mlist size", mList.size() + "");
                        if (mList.size() > 0){
                            arrangeNotifications(context);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("ON ERROR",  "error");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ON COMPLETE",  "COMPLETE");
                    }
                });

//        disposable.dispose();
    }

    public void cancelReleaseReminder(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0);
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setReleaseReminder(Context context, String time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);

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
