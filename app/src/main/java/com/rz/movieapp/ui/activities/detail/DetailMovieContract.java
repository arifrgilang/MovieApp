package com.rz.movieapp.ui.activities.detail;

import com.rz.movieapp.data.model.MovieObject;

public interface DetailMovieContract {
    interface View{
        void setFavoriteState();
        void showLoading(Boolean condition);
        void setView(MovieObject results);
    }
    interface Presenter{
        void getMovieDetail(String id);
        void onDestroyComposite();
    }
}
