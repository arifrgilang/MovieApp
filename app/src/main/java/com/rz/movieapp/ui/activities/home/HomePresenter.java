package com.rz.movieapp.ui.activities.home;

import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingFragment;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingFragment;

import javax.inject.Inject;

public class HomePresenter implements HomeContract.Presenter{

    static final String F_NOW_PLAYING = "nowplaying";
    static final String F_UPCOMING = "upcoming";
    static final String F_SEARCH = "search";

    private HomeContract.View view;

    @Inject HomePresenter(HomeContract.View view){
        this.view = view;
    }

    @Override
    public void changeFragment(String fragmentName) {
        switch (fragmentName){
            case F_NOW_PLAYING:
                view.setFragment(new NowPlayingFragment());
                break;
            case F_UPCOMING:
                view.setFragment(new UpcomingFragment());
                break;
        }
    }
}
