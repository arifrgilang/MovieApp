package com.rz.movieapp.di;

import android.app.Application;

import com.rz.movieapp.di.component.AppComponent;
import com.rz.movieapp.di.component.DaggerAppComponent;
import com.rz.movieapp.di.modules.NetworkModule;

public class App extends Application {
    private static App app;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

    public static App app(){
        return app;
    }

    public AppComponent appComponent(){
        return appComponent;
    }
}
