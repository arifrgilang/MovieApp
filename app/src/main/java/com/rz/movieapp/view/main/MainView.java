package com.rz.movieapp.view.main;

import com.rz.movieapp.model.MovieObject;

import java.util.ArrayList;

public interface MainView {
    void showLoading(Boolean condition);
    void setView(ArrayList<MovieObject> results);
}
