package com.rz.movieapp.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

@Dao
public interface MovieDao {

    @Query("SELECT COUNT(*) FROM " + Movie.TABLE_NAME)
    int count();

    @Insert
    long insert(Movie movie);

    @Insert
    long[] insertAll(Movie[] movies);

    @Query("SELECT * FROM " + Movie.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COL_MOVIE_ID + " = :id")
    Cursor selectById(String id);

    @Query("DELETE FROM " + Movie.TABLE_NAME + " WHERE " + Movie.COL_MOVIE_ID + " = :id")
    int deleteById(String id);

    @Update
    int update(Movie movie);

}
