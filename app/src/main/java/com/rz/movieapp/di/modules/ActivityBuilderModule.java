package com.rz.movieapp.di.modules;

import com.rz.movieapp.di.modules.activity.ActivityViewModule;
import com.rz.movieapp.di.modules.activity.ActivityPresenterModule;
import com.rz.movieapp.view.detail.DetailMovieActivity;
import com.rz.movieapp.view.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {ActivityViewModule.class, ActivityPresenterModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {ActivityViewModule.class, ActivityPresenterModule.class})
    abstract DetailMovieActivity bindDetailMovieActivity();
}
