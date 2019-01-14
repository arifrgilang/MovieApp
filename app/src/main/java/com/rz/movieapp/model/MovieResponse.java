package com.rz.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {
    @SerializedName("results")
    private ArrayList<MovieObject> results = new ArrayList<>();

    public ArrayList<MovieObject> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieObject> results) {
        this.results = results;
    }
}
