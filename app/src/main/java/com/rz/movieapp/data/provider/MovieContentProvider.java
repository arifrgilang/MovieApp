package com.rz.movieapp.data.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rz.movieapp.data.room.Movie;
import com.rz.movieapp.data.room.MovieDao;
import com.rz.movieapp.data.room.MovieDb;

import java.util.ArrayList;

public class MovieContentProvider extends ContentProvider {

    private static final int CODE_MOVIE_DIR = 1;
    private static final int CODE_MOVIE_ITEM = 2;
    public static final String AUTHORITY = "com.rz.movieapp";
    public static final Uri URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(Movie.TABLE_NAME)
            .build();
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME, CODE_MOVIE_DIR);
        URI_MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, 
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final int code = URI_MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            MovieDao cheese = MovieDb.getInstance(context).movie();
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = cheese.selectAll();
            } else {
                cursor = cheese.selectById(Long.toString(ContentUris.parseId(uri)));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Movie.TABLE_NAME;
            case CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Movie.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = MovieDb.getInstance(context).movie()
                        .insert(Movie.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, 
                      @Nullable String selection, 
                      @Nullable String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = MovieDb.getInstance(context).movie()
                        .deleteById(Long.toString(ContentUris.parseId(uri)));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, 
                      @Nullable ContentValues values, 
                      @Nullable String selection, 
                      @Nullable String[] selectionArgs) {

        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Movie movie = Movie.fromContentValues(values);
                movie.id = ContentUris.parseId(uri);
                final int count = MovieDb.getInstance(context).movie()
                        .update(movie);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final MovieDb database = MovieDb.getInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final MovieDb database = MovieDb.getInstance(context);
                final Movie[] movies = new Movie[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    movies[i] = Movie.fromContentValues(valuesArray[i]);
                }
                return database.movie().insertAll(movies).length;
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
