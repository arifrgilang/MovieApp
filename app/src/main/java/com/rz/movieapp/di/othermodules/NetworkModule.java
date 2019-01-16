package com.rz.movieapp.di.othermodules;

import com.rz.movieapp.BuildConfig;
import com.rz.movieapp.data.api.MovieDBClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    // Service
    @Provides
    @Singleton
    Class<MovieDBClient> provideMovieDBClientClass(){
        return MovieDBClient.class;
    }

    @Provides
    @Singleton
    MovieDBClient provideMovieDBClient(Retrofit retrofit, Class<MovieDBClient> client){
        return retrofit.create(client);
    }

    // Retrofit
    @Provides
    @Singleton
    HttpLoggingInterceptor provideLogging(){
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logging){
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging).build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient client){
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder){
        return builder.build();
    }
}
