package com.rz.movieapp.api;

import com.rz.movieapp.BuildConfig;
import com.rz.movieapp.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDBClient {
    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Observable<MovieResponse> getListMovie(@Query("query") String query);
}