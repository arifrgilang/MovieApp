package com.rz.movieapp.ui.activities.home;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {
    @Provides
    HomeContract.View provideHomeView(HomeActivity homeActivity){
        return homeActivity;
    }

    @Provides
    HomeContract.Presenter provideHomePresenter(HomeContract.View view){
        return new HomePresenter(view);
    }
}
