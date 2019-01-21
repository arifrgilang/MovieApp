package com.rz.movieapp.ui.fragments.favorite;

import com.rz.movieapp.data.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteModule {

    @Provides
    FavoriteContract.View provideFavView(FavoriteFragment favoriteFragment){
        return favoriteFragment;
    }

    @Provides
    FavoriteContract.Presenter provideFavPresenter(FavoriteContract.View view){
        return new FavoritePresenter(view);
    }
}
