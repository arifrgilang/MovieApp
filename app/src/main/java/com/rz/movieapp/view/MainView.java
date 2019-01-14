package com.rz.movieapp.view;

import com.rz.movieapp.model.MovieObject;
import com.rz.movieapp.model.MovieResponse;

import java.util.ArrayList;

public interface MainView {
    public void showLoading(Boolean condition);
    public void setView(ArrayList<MovieObject> results);
}
