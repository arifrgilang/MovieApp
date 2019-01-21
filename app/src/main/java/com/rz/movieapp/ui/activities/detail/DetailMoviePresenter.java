package com.rz.movieapp.ui.activities.detail;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rz.movieapp.R;
import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.rz.movieapp.db.DbContract.FavColumns.CONTENT_URI;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_LANGUAGE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_OVERVIEW;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_POSTER;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_RATING;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_R_DATE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_TITLE;

public class DetailMoviePresenter implements DetailMovieContract.Presenter{

    private final String TAG = "DetailMoviePresenter";

    private DetailMovieContract.View view;
    private MovieDBClient service;
    private Disposable disposable;
    private Context ctx;

    @Inject DetailMoviePresenter(DetailMovieContract.View view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void onDestroyComposite(){
        disposable.dispose();
    }

    @Override
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void checkFavorite(String movieId) {
        Uri uri = Uri.parse(CONTENT_URI+ "/" + movieId);
        boolean fav = false;
        String selection = MOVIE_ID +" =?";
        String[] selectionArgs = new String[]{movieId};
        Cursor cursor = ctx.getContentResolver().query(uri,
                null,
                selection,
                selectionArgs,
                null);

        String getMovieId = null;

        if(cursor.getCount() > 0 ){
            view.setFavorite(true);
        }
    }

    @Override
    public void addToFavorite(MovieObject movieObject) {
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, movieObject.getId());
        values.put(MOVIE_TITLE, movieObject.getOriginal_title());
        values.put(MOVIE_RATING, movieObject.getVote_average());
        values.put(MOVIE_POSTER, movieObject.getPoster_path());
        values.put(MOVIE_LANGUAGE, movieObject.getOriginal_language());
        values.put(MOVIE_R_DATE, movieObject.getRelease_date());
        values.put(MOVIE_OVERVIEW, movieObject.getOverview());

        ctx.getContentResolver().insert(CONTENT_URI, values);
    }

    @Override
    public void removeFromFavorite(MovieObject movieObject) {
        Uri uri = Uri.parse(CONTENT_URI + "");
        Log.d(TAG, uri.toString());
        ctx.getContentResolver().delete(uri, MOVIE_ID, new String[]{movieObject.getId()});
    }

    @Override
    public void getMovieDetail(String id){
        view.showLoading(true);
        disposable = service.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieObject>() {
                    @Override
                    public void onNext(MovieObject movieResponse) {
                        Log.d(TAG, "onNext");
                        view.showLoading(false);
                        movieResponse.setPoster_path("https://image.tmdb.org/t/p/w342"
                                + movieResponse.getPoster_path());
                        view.setView(movieResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        view.showLoading(false);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }
}
