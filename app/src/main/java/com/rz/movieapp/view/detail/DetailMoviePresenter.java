package com.rz.movieapp.view.detail;

import android.util.Log;

import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.model.MovieObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class DetailMoviePresenter {

    private final String TAG = "DetailMoviePresenter";

    private DetailMovieView view;
    private MovieDBClient service;
    private Disposable disposable;

    DetailMoviePresenter(DetailMovieView view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    void onDestroyComposite(){
        disposable.dispose();
    }

    void getMovieDetail(String id){
        view.showLoading(true);
        disposable = service.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieObject>() {
                    @Override
                    public void onNext(MovieObject movieResponse) {
                        Log.d(TAG, "onNext");
                        view.showLoading(false);
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
