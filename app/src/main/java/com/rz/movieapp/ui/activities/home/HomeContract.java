package com.rz.movieapp.ui.activities.home;


import android.support.v4.app.Fragment;

public interface HomeContract {
    interface View{
        void setFragment(Fragment fragment);
        void initBottomNav();
        void setActionBarTitle(String title);
    }

    interface Presenter{
        void changeFragment(String fragmentName);
    }
}
