package com.rz.movieapp.ui.fragments.nowplaying;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

public interface NowPlayingContract {
    interface View{
        void showLoading(Boolean condition);
        void setView(ArrayList<MovieObject> results);
    }
    interface Presenter{
        void getNowPlayingMovies();
        void onDestroyComposite();
    }
}
