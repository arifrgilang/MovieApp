package com.rz.movieapp.di.modules.activity;

import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.view.detail.DetailMovieContract;
import com.rz.movieapp.view.detail.DetailMoviePresenter;
import com.rz.movieapp.view.main.MainContract;
import com.rz.movieapp.view.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityPresenterModule {

    @Provides
    MainPresenter provideMainPresenter(MainContract.View view, MovieDBClient service){
        return new MainPresenter(view, service);
    }

    @Provides
    DetailMoviePresenter provideDetailPresenter(DetailMovieContract.View view, MovieDBClient service){
        return new DetailMoviePresenter(view, service);
    }
}
