package com.rz.movieapp.ui.activities.setting;

class SettingContract {
    interface View{
        void setSwitch(boolean condition);
        void setCurrentCondition(boolean condition);
    }

    interface Presenter{
        void turnOnDailyAlarm();
        void turnOffDailyAlarm();
        void checkCondition();
    }
}
