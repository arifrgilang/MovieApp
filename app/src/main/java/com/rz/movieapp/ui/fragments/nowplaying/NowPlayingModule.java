package com.rz.movieapp.ui.fragments.nowplaying;

import com.rz.movieapp.data.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class NowPlayingModule {

    @Provides
    NowPlayingContract.View provideMainView(NowPlayingFragment nowPlayingFragment){
        return nowPlayingFragment;
    }

    @Provides
    NowPlayingContract.Presenter provideMainPresenter(NowPlayingContract.View view, MovieDBClient service){
        return new NowPlayingPresenter(view, service) ;
    }
}
