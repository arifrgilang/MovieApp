package com.rz.favoritemodule.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String AUTHORITY = "com.rz.movieapp";
    public static final String SCHEME = "content";

    public static final class FavColumns implements BaseColumns {
        public static String TABLE_FAVORITE = "favorite";

        public static String MOVIE_ID = "id";
        public static String MOVIE_TITLE = "original_title";
        public static String MOVIE_RATING = "vote_average";
        public static String MOVIE_POSTER = "poster_path";
        public static String MOVIE_LANGUAGE = "original_language";
        public static String MOVIE_R_DATE = "release_date";
        public static String MOVIE_OVERVIEW = "overview";

        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
