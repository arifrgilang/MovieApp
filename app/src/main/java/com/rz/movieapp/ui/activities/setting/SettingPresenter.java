package com.rz.movieapp.ui.activities.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.rz.movieapp.R;
import com.rz.movieapp.reminder.DailyReminderReceiver;

import static android.content.Context.MODE_PRIVATE;

public class SettingPresenter implements SettingContract.Presenter
{

    private static final String DAILY_SP = "dailySP";
    private static final String DAILY_REMINDER = "daily";

    private Context ctx;
    private SettingContract.View view;
    private DailyReminderReceiver dailyReminder;
    private SharedPreferences sp;

    SettingPresenter(Context ctx, SettingContract.View view){
        this.ctx = ctx;
        this.view = view;
        this.dailyReminder = new DailyReminderReceiver();
        this.sp = this.ctx.getSharedPreferences(DAILY_SP, MODE_PRIVATE);
    }

    private void putBoolToSharedPreferences(boolean condition){
        SharedPreferences.Editor spEditor = this.sp.edit();
        spEditor.putBoolean(DAILY_REMINDER, condition);
        spEditor.apply();
    }

    @Override
    public void turnOnDailyAlarm() {
        putBoolToSharedPreferences(true);
        String time = "07:00";
        String title = ctx.getResources().getString(R.string.daily_title);
        String message = ctx.getResources().getString(R.string.daily_desc);
        view.setCurrentCondition(true);
        dailyReminder.setDailyReminder(ctx, title, time, message);
    }

    @Override
    public void turnOffDailyAlarm() {
        putBoolToSharedPreferences(false);
        view.setCurrentCondition(false);
        dailyReminder.cancelDailyReminder(ctx);
    }

    @Override
    public void checkCondition() {
        boolean savedCondition = sp.getBoolean(DAILY_REMINDER, false);
        view.setCurrentCondition(savedCondition);
        view.setSwitch(savedCondition);
    }
}
