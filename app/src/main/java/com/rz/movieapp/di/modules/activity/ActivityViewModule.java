package com.rz.movieapp.di.modules.activity;

import com.rz.movieapp.view.detail.DetailMovieActivity;
import com.rz.movieapp.view.detail.DetailMovieContract;
import com.rz.movieapp.view.main.MainActivity;
import com.rz.movieapp.view.main.MainContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ActivityViewModule {
    @Binds
    abstract MainContract.View provideMainView(MainActivity mainActivity);

    @Binds
    abstract DetailMovieContract.View provideDetailView(DetailMovieActivity detailMovieActivity);
}
