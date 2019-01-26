package com.rz.movieapp.reminder;

import com.rz.movieapp.BuildConfig;
import com.rz.movieapp.data.api.MovieDBClient;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupportRetrofit {

    public static MovieDBClient provideMovieDBClient(){
        return provideRetrofit().create(MovieDBClient.class);
    }

    // Retrofit

    private static HttpLoggingInterceptor provideLogging(){
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(provideLogging()).build();
    }

    private static Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder()
                .client(provideHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL);
    }

    private static Retrofit provideRetrofit(){
        return provideRetrofitBuilder().build();
    }
}
