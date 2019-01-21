package com.rz.movieapp.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

@Entity(tableName = Movie.TABLE_NAME)
public class Movie {
    public static final String TABLE_NAME = "movies";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_MOVIE_ID = "id";
    public static final String COL_TITLE = "original_title";
    public static final String COL_VOTE = "vote_average";
    public static final String COL_URL_POSTER = "poster_path";
    public static final String COL_LANGUAGE = "original_language";
    public static final String COL_RELEASE_DATE = "release_date";
    public static final String COL_OVERVIEW = "overview";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COL_ID) public long id;

    @ColumnInfo(name = COL_MOVIE_ID) public String movieId;
    @ColumnInfo(name = COL_TITLE) public String title;
    @ColumnInfo(name = COL_VOTE) public String vote;
    @ColumnInfo(name = COL_URL_POSTER) public String urlPoster;
    @ColumnInfo(name = COL_LANGUAGE) public String language;
    @ColumnInfo(name = COL_RELEASE_DATE) public String releaseDate;
    @ColumnInfo(name = COL_OVERVIEW) public String overview;

    public static Movie fromContentValues(ContentValues values){
        final Movie movie = new Movie();
        if(values.containsKey(COL_ID)) movie.id = values.getAsLong(COL_ID);
        if(values.containsKey(COL_ID)) movie.movieId = values.getAsString(COL_MOVIE_ID);
        if(values.containsKey(COL_TITLE)) movie.title = values.getAsString(COL_TITLE);
        if(values.containsKey(COL_VOTE)) movie.vote = values.getAsString(COL_VOTE);
        if(values.containsKey(COL_URL_POSTER)) movie.urlPoster = values.getAsString(COL_URL_POSTER);
        if(values.containsKey(COL_LANGUAGE)) movie.language = values.getAsString(COL_LANGUAGE);
        if(values.containsKey(COL_RELEASE_DATE)) movie.releaseDate = values.getAsString(COL_RELEASE_DATE);
        if(values.containsKey(COL_OVERVIEW)) movie.overview = values.getAsString(COL_OVERVIEW);

        return movie;
    }
}
