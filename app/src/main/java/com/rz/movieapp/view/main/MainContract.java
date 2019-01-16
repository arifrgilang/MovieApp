package com.rz.movieapp.view.main;

import com.rz.movieapp.view.base.BaseView;
import com.rz.movieapp.model.MovieObject;

import java.util.ArrayList;

public interface MainContract {
    interface View extends BaseView {
        void showLoading(Boolean condition);
        void showNotFound(Boolean condition);
        void setView(ArrayList<MovieObject> results);
    }

    interface Presenter{
        void getListMovie(String query);
        void onDestroyComposite();
    }
}
