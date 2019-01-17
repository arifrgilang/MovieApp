package com.rz.movieapp.ui.fragments.nowplaying;

import android.util.Log;

import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class NowPlayingPresenter implements NowPlayingContract.Presenter{
    private final String TAG = "NowPlayingPresenter";

    private NowPlayingContract.View view;
    private MovieDBClient service;
    private Disposable disposable;

    @Inject NowPlayingPresenter(NowPlayingContract.View view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void getNowPlayingMovies() {
        view.showLoading(true);
        disposable = service.getNowPlayingMovies()
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

    @Override
    public void onDestroyComposite() {
        disposable.dispose();
    }
}
