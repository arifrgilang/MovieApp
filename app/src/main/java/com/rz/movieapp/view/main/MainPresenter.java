package com.rz.movieapp.view.main;

import android.util.Log;

import com.rz.movieapp.api.MovieDBClient;
import com.rz.movieapp.model.MovieResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class MainPresenter {
    private final String TAG = "MainPresenter";

    private MainView view;
    private MovieDBClient service;
    private Disposable disposable;

    MainPresenter(MainView view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    void onDestroyComposite(){
        disposable.dispose();
    }

    void getListMovie(String query){
        view.showLoading(true);
        disposable = service.getListMovie(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieResponse>() {
                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        Log.d(TAG, "onNext");
                        view.showLoading(false);
                        view.setView(movieResponse.getResults());
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
