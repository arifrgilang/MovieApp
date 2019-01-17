package com.rz.movieapp.ui.fragments.search;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

public interface SearchContract {
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
