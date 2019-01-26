package com.rz.movieapp.ui.activities.setting;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.rz.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingActivity extends AppCompatActivity implements SettingContract.View{

    private boolean isDailyChecked;

    @BindView(R.id.setting_layout) LinearLayout settingLayout;
    @BindView(R.id.switch_daily_reminder) Switch mSwitchDailyReminder;

    SettingContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        mPresenter = new SettingPresenter(this, this);
        mPresenter.checkCondition();
    }

    @OnCheckedChanged(R.id.switch_daily_reminder)
    public void setDailyReminder(boolean isChecked){
        if(isChecked){
            if(!isDailyChecked){
                mPresenter.turnOnDailyAlarm();
                showSnackbar(getString(R.string.snackbar_daily_on));
            }
        } else {
            if(isDailyChecked){
                mPresenter.turnOffDailyAlarm();
                showSnackbar(getString(R.string.snackbar_daily_off));
            }
        }
    }

    private void showSnackbar(String message){
        Snackbar.make(settingLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setSwitch(boolean condition) {
        mSwitchDailyReminder.setChecked(condition);
    }

    @Override
    public void setCurrentCondition(boolean condition) {
        isDailyChecked = condition;
    }
}
