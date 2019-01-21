package com.rz.movieapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.provider.BaseColumns._ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_LANGUAGE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_OVERVIEW;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_POSTER;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_RATING;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_R_DATE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_TITLE;
import static com.rz.movieapp.db.DbContract.FavColumns.TABLE_FAVORITE;

@Singleton
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_fav";
    private static final int DB_VERSION = 1;
    private static final String SQL_DROP_FAV_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVORITE;
    private static final String SQL_CREATE_FAV_TABLE = String
            .format("CREATE TABLE IF NOT EXISTS %s" +
                    " (%s TEXT NOT NULL PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
                    TABLE_FAVORITE,
                    MOVIE_ID,
                    MOVIE_TITLE,
                    MOVIE_RATING,
                    MOVIE_POSTER,
                    MOVIE_LANGUAGE,
                    MOVIE_R_DATE,
                    MOVIE_OVERVIEW);

    @Inject public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_FAV_TABLE);
        onCreate(db);
    }

}
