package com.rz.movieapp.ui.activities.detail;

import android.content.Context;

import com.rz.movieapp.data.model.MovieObject;

public interface DetailMovieContract {
    interface View{
        void setFavoriteState();
        void showLoading(Boolean condition);
        void setView(MovieObject results);
        void setFavorite(Boolean condition);
    }
    interface Presenter{
        void getMovieDetail(String id);
        void onDestroyComposite();
        void setContext(Context ctx);
        void checkFavorite(String movieId);
        void addToFavorite(MovieObject movieObject);
        void removeFromFavorite(MovieObject movieObject);
    }
}
