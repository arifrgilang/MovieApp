package com.rz.movieapp.ui.activities.detail;

import android.content.res.Resources;
import android.util.Log;

import com.rz.movieapp.R;
import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailMoviePresenter implements DetailMovieContract.Presenter{

    private final String TAG = "DetailMoviePresenter";

    private DetailMovieContract.View view;
    private MovieDBClient service;
    private Disposable disposable;

    @Inject DetailMoviePresenter(DetailMovieContract.View view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void onDestroyComposite(){
        disposable.dispose();
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
