package com.rz.movieapp.view.detail;

import com.rz.movieapp.model.MovieObject;

public interface DetailMovieView {
    void showLoading(Boolean condition);
    void setView(MovieObject results);
}
