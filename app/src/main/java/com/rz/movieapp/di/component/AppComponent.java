package com.rz.movieapp.di.component;

import android.app.Application;

import com.rz.movieapp.di.App;
import com.rz.movieapp.di.modules.ActivityBuilderModule;
import com.rz.movieapp.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        NetworkModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
