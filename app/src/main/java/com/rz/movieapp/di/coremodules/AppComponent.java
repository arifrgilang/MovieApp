package com.rz.movieapp.di.coremodules;

import com.rz.movieapp.MyApp;
import com.rz.movieapp.di.othermodules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        NetworkModule.class,
        ActivityBuilder.class,})

public interface AppComponent extends AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApp>{}
}
