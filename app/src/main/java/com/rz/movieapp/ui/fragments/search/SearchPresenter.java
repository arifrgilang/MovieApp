package com.rz.movieapp.ui.fragments.search;

import android.util.Log;

import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.data.model.MovieResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class SearchPresenter implements SearchContract.Presenter{
    private final String TAG = "SearchPresenter";

    private SearchContract.View view;
    private MovieDBClient service;
    private Disposable disposable;

    @Inject
    SearchPresenter(SearchContract.View view, MovieDBClient service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void onDestroyComposite(){
        disposable.dispose();
    }

    @Override
    public void getListMovie(String query){
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
                        view.showNotFound(movieResponse.getResults().size() == 0);
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
