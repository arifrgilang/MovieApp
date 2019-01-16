package com.rz.movieapp.di.coremodules;

import com.rz.movieapp.di.othermodules.NetworkModule;
import com.rz.movieapp.view.detail.DetailMovieActivity;
import com.rz.movieapp.view.detail.DetailMovieModule;
import com.rz.movieapp.view.main.MainActivity;
import com.rz.movieapp.view.main.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = DetailMovieModule.class)
    abstract DetailMovieActivity bindDetailMovieActivity();
}
