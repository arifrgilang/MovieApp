package com.rz.movieapp.view.detail;

import com.rz.movieapp.model.MovieObject;

public interface DetailMovieContract {
    interface View{
        void showLoading(Boolean condition);
        void setView(MovieObject results);
    }
    interface Presenter{
        void getMovieDetail(String id);
        void onDestroyComposite();
    }
}
