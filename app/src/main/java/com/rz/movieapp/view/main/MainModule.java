package com.rz.movieapp.view.main;

import com.rz.movieapp.api.MovieDBClient;

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
