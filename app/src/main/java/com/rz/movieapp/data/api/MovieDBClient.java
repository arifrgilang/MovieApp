package com.rz.movieapp.data.api;

import com.rz.movieapp.BuildConfig;
import com.rz.movieapp.data.model.MovieObject;
import com.rz.movieapp.data.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBClient {

    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Observable<MovieResponse> getListMovie(@Query("query") String query);

    @GET("movie/{id}?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Observable<MovieObject> getMovieDetail(@Path("id") String id);

    @GET("movie/now_playing?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Observable<MovieResponse> getNowPlayingMovies();

    @GET("movie/upcoming?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Observable<MovieResponse> getUpcomingMovies();
}
