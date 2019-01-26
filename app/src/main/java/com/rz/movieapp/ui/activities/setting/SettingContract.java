package com.rz.movieapp.ui.activities.setting;

interface SettingContract {
    interface View{
        void setDailySwitch(boolean condition);
        void setDailyCondition(boolean condition);
        void setReleaseSwitch(boolean condition);
        void setReleaseCondition(boolean condition);
    }

    interface Presenter{
        void turnOnDailyAlarm();
        void turnOffDailyAlarm();
        void turnOnReleaseAlarm();
        void turnOffReleaseAlarm();
        void checkCondition();
    }
}
