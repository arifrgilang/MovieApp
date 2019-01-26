package com.rz.movieapp.di.coremodules;

import com.rz.movieapp.di.othermodules.NetworkModule;
import com.rz.movieapp.reminder.ReleaseReminderReceiver;
import com.rz.movieapp.ui.activities.detail.DetailMovieActivity;
import com.rz.movieapp.ui.activities.detail.DetailMovieModule;
import com.rz.movieapp.ui.activities.setting.SettingActivity;
import com.rz.movieapp.ui.fragments.favorite.FavoriteFragment;
import com.rz.movieapp.ui.fragments.favorite.FavoriteModule;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingFragment;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingModule;
import com.rz.movieapp.ui.activities.home.HomeActivity;
import com.rz.movieapp.ui.activities.home.HomeModule;
import com.rz.movieapp.ui.fragments.search.SearchFragment;
import com.rz.movieapp.ui.fragments.search.SearchModule;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingFragment;
import com.rz.movieapp.ui.fragments.upcoming.UpcomingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {
    //Activity
    @ContributesAndroidInjector(modules = DetailMovieModule.class)
    abstract DetailMovieActivity bindDetailMovieActivity();

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity bindHomeActivity();

    // Fragment
    @ContributesAndroidInjector(modules = NowPlayingModule.class)
    abstract NowPlayingFragment bindNowPlayingFragment();

    @ContributesAndroidInjector(modules = UpcomingModule.class)
    abstract UpcomingFragment bindUpcomingFragment();

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment bindSearchFragment();

    @ContributesAndroidInjector(modules = FavoriteModule.class)
    abstract FavoriteFragment bindFavoriteFragment();
}
