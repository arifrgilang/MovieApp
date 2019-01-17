package com.rz.movieapp.di;

import android.app.Activity;
import android.app.Application;

import com.rz.movieapp.di.component.AppComponent;
import com.rz.movieapp.di.component.DaggerAppComponent;
import com.rz.movieapp.di.modules.NetworkModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {
//    private static App app;
//    private AppComponent appComponent;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
//        app = this;
//
//        appComponent = DaggerAppComponent.builder()
//                .networkModule(new NetworkModule())
//                .build();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

//    public static App app(){
//        return app;
//    }
//
//    public AppComponent appComponent(){
//        return appComponent;
//    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
