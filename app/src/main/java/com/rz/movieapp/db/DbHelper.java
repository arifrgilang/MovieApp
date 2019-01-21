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
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
                    TABLE_FAVORITE,
                    _ID,
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

    public ArrayList<MovieObject> query() throws NullPointerException{
        ArrayList<MovieObject> mList = new ArrayList<>();
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            MovieObject movieObject;

            cursor = db.query(TABLE_FAVORITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " DESC");

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
                db.close();
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String selection = MOVIE_ID +" =?";
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
        } finally {
            if (cursor != null){ cursor.close(); }
        }
        return cursor;
    }

    public Cursor queryProvider() throws NullPointerException{
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_FAVORITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    _ID + " DESC");
            db.close();
        } catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null){ cursor.close(); }
        }
        return cursor;
    }

    public long insertProvider(ContentValues values){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.insert(TABLE_FAVORITE,
                null,
                values);
    }

    public int updateProvider(String movieId, ContentValues values){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.update(TABLE_FAVORITE,
                values,
                MOVIE_ID + " = ?",
                new String[]{movieId});
    }

    public int deleteProvider(String movieId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_FAVORITE,
                MOVIE_ID + " = ?",
                new String[]{movieId});
    }
}
