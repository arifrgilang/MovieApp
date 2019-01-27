package com.rz.movieapp.ui.activities.home;

import android.support.v4.app.Fragment;

import com.rz.movieapp.ui.fragments.favorite.FavoriteFragment;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingFragment;
import com.rz.movieapp.ui.fragments.search.SearchFragment;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingFragment;

import java.util.HashMap;

import javax.inject.Inject;

public class HomePresenter implements HomeContract.Presenter{

    static final String F_NOW_PLAYING = "nowplaying";
    static final String F_UPCOMING = "upcoming";
    static final String F_SEARCH = "search";
    static final String F_FAVORITE = "favorite";

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
            case F_SEARCH:
                view.setFragment(new SearchFragment());
                break;
            case F_FAVORITE:
                view.setFragment(new FavoriteFragment());
        }
    }
}
