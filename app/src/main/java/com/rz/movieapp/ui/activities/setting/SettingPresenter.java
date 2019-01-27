package com.rz.movieapp.ui.activities.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.rz.movieapp.R;
import com.rz.movieapp.reminder.DailyReminderReceiver;
import com.rz.movieapp.reminder.ReleaseReminderReceiver;

import static android.content.Context.MODE_PRIVATE;

public class SettingPresenter implements SettingContract.Presenter {

    private static final String SETTING_SP = "dailySP";
    private static final String DAILY_REMINDER = "daily";
    private static final String RELEASE_REMINDER = "release";

    private Context ctx;
    private SettingContract.View view;
    private DailyReminderReceiver dailyReminder;
    private ReleaseReminderReceiver releaseReminder;
    private SharedPreferences sp;

    SettingPresenter(Context ctx, SettingContract.View view){
        this.ctx = ctx;
        this.view = view;
        this.dailyReminder = new DailyReminderReceiver();
        this.releaseReminder = new ReleaseReminderReceiver();
        this.sp = this.ctx.getSharedPreferences(SETTING_SP, MODE_PRIVATE);
    }

    private void putBoolToSharedPreferences(String type, boolean condition){
        SharedPreferences.Editor spEditor = this.sp.edit();
        spEditor.putBoolean(type, condition);
        spEditor.apply();
    }

    @Override
    public void turnOnDailyAlarm() {
        putBoolToSharedPreferences(DAILY_REMINDER,true);
        String time = "07:00";
        String title = ctx.getResources().getString(R.string.daily_title);
        String message = ctx.getResources().getString(R.string.daily_desc);
        view.setDailyCondition(true);
        dailyReminder.setDailyReminder(ctx, title, time, message);
    }

    @Override
    public void turnOffDailyAlarm() {
        putBoolToSharedPreferences(DAILY_REMINDER,false);
        view.setDailyCondition(false);
        dailyReminder.cancelDailyReminder(ctx);
    }

    @Override
    public void turnOnReleaseAlarm() {
        putBoolToSharedPreferences(RELEASE_REMINDER,true);
        String time = "08:00";
        view.setReleaseCondition(true);
        releaseReminder.setReleaseReminder(ctx, time);
    }

    @Override
    public void turnOffReleaseAlarm() {
        putBoolToSharedPreferences(RELEASE_REMINDER, false);
        view.setReleaseCondition(false);
        releaseReminder.cancelReleaseReminder(ctx);
    }

    @Override
    public void checkCondition() {
        boolean savedDailyCondition = sp.getBoolean(DAILY_REMINDER, false);
        view.setDailyCondition(savedDailyCondition);
        view.setDailySwitch(savedDailyCondition);

        boolean savedReleaseCondition = sp.getBoolean(RELEASE_REMINDER, false);
        view.setReleaseCondition(savedReleaseCondition);
        view.setReleaseSwitch(savedReleaseCondition);
    }
}
