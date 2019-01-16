package com.rz.movieapp.ui.activities.main;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

public interface MainContract {
    interface View{
        void showLoading(Boolean condition);
        void showNotFound(Boolean condition);
        void setView(ArrayList<MovieObject> results);
    }

    interface Presenter{
        void getListMovie(String query);
        void onDestroyComposite();
    }
}
