package com.rz.movieapp.ui.activities.main;

import com.rz.movieapp.data.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainContract.View provideMainView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainContract.Presenter provideMainPresenter(MainContract.View mainView, MovieDBClient service){
        return new MainPresenter(mainView, service) ;
    }
}
