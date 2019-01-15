package com.rz.movieapp.di.component;

import com.rz.movieapp.di.modules.NetworkModule;
import com.rz.movieapp.view.detail.DetailMovieActivity;
import com.rz.movieapp.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailMovieActivity detailMovieActivity);
}
