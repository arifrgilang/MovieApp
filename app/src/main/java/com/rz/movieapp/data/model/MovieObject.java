package com.rz.movieapp.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.rz.movieapp.db.DbContract;

public class MovieObject implements Parcelable {
    @SerializedName("id") private String id;
    @SerializedName("original_title") private String original_title;
    @SerializedName("vote_average") private String vote_average;
    @SerializedName("poster_path") private String poster_path;
    @SerializedName("original_language") private String original_language;
    @SerializedName("release_date") private String release_date;
    @SerializedName("overview") private String overview;

    public MovieObject(){}

    protected MovieObject(Parcel in) {
        id = in.readString();
        original_title = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        release_date = in.readString();
        overview = in.readString();
    }

    public MovieObject(Cursor cursor){
        id = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_ID);
        original_title = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_TITLE);
        vote_average = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_RATING);
        poster_path = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_POSTER);
        original_language = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_LANGUAGE);
        release_date = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_R_DATE);
        overview = DbContract.getColumnString(cursor, DbContract.FavColumns.MOVIE_OVERVIEW);
    }

    public static final Creator<MovieObject> CREATOR = new Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
    }

    // getter & setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getVote_average() {
        return vote_average;
    }
    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
}
