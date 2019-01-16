package com.rz.movieapp.ui.fragments.search;

import com.rz.movieapp.data.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    SearchContract.View provideMainView(SearchFragment searchFragment){
        return searchFragment;
    }

    @Provides
    SearchContract.Presenter provideMainPresenter(SearchContract.View mainView, MovieDBClient service){
        return new SearchPresenter(mainView, service) ;
    }
}
