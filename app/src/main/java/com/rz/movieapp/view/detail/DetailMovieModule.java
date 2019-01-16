package com.rz.movieapp.view.detail;

import com.rz.movieapp.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailMovieModule {

    @Provides
    DetailMovieContract.View provideDetailMovieView(DetailMovieActivity detailMovieActivity){
        return detailMovieActivity;
    }

    @Provides
    DetailMovieContract.Presenter provideDetailMoviePresenter(DetailMovieContract.View view, MovieDBClient service){
        return new DetailMoviePresenter(view, service);
    }
}
