package com.rz.movieapp.ui.fragments.upcoming;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

public interface UpcomingContract {
    interface View{
        void showLoading(Boolean condition);
        void setView(ArrayList<MovieObject> results);
    }
    interface Presenter{
        void getUpcomingMovies();
        void onDestroyComposite();
    }
}
