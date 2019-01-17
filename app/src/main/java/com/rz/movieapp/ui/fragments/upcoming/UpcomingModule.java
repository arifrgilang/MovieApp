package com.rz.movieapp.ui.fragments.upcoming;

import com.rz.movieapp.data.api.MovieDBClient;

import dagger.Module;
import dagger.Provides;

@Module
public class UpcomingModule {

    @Provides
    UpcomingContract.View provideMainView(UpcomingFragment upcomingFragment){
        return upcomingFragment;
    }

    @Provides
    UpcomingContract.Presenter provideMainPresenter(UpcomingContract.View view, MovieDBClient service){
        return new UpcomingPresenter(view, service) ;
    }
}
