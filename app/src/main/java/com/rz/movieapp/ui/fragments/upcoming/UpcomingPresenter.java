package com.rz.movieapp.ui.fragments.upcoming;

import android.util.Log;

import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieResponse;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingContract;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class UpcomingPresenter implements UpcomingContract.Presenter{
    private final String TAG = "UpcomingPresenter";

    private UpcomingContract.View view;
    private MovieDBClient service;
    private Disposable disposable;

    @Inject
    UpcomingPresenter(UpcomingContract.View view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void getUpcomingMovies() {
        view.showLoading(true);
        disposable = service.getUpcomingMovies()
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
