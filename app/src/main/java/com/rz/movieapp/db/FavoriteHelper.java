package com.rz.movieapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rz.movieapp.data.model.MovieObject;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_ID;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_LANGUAGE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_OVERVIEW;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_POSTER;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_RATING;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_R_DATE;
import static com.rz.movieapp.db.DbContract.FavColumns.MOVIE_TITLE;
import static com.rz.movieapp.db.DbContract.FavColumns.TABLE_FAVORITE;

public class FavoriteHelper {

    private Context context;
    private DbHelper dbHelper;

    private SQLiteDatabase db;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException{
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public ArrayList<MovieObject> query() throws NullPointerException{
        ArrayList<MovieObject> mList = new ArrayList<>();
        Cursor cursor = null;

        try {
            MovieObject movieObject;

            cursor = db.query(TABLE_FAVORITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0){
                do {
                    movieObject = new MovieObject();

                    movieObject.setId(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                    movieObject.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                    movieObject.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_RATING)));
                    movieObject.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER)));
                    movieObject.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_LANGUAGE)));
                    movieObject.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_R_DATE)));
                    movieObject.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));

                    mList.add(movieObject);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null){ cursor.close(); }
        }
        return mList;
    }

    public Cursor queryByIdProvider(String movieId) throws NullPointerException{
        Cursor cursor = null;
        String selection = MOVIE_ID + "=?";
        String[] selectionArgs = new String[]{movieId};

        try {
            cursor = db.query(TABLE_FAVORITE,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        } catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return cursor;
    }

    public Cursor queryProvider(String selection, String[] selectionArgs) throws NullPointerException{
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_FAVORITE,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        } catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return cursor;
    }

    public long insertProvider(ContentValues values){
        long result = db.insert(TABLE_FAVORITE,
                null,
                values);
        return result;
    }

    public int updateProvider(String whereClause, String[] whereArgs, ContentValues values){
        return db.update(TABLE_FAVORITE,
                values,
                whereClause + "=?",
                whereArgs);
    }

    public int deleteProvider(String whereClause, String[] whereArgs){
        return db.delete(TABLE_FAVORITE,
                whereClause + "=?"
                , whereArgs);
    }
}
