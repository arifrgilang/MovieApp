package com.rz.movieapp.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.rz.movieapp.db.DbContract.AUTHORITY;
import static com.rz.movieapp.db.DbContract.FavColumns.CONTENT_URI;
import static com.rz.movieapp.db.DbContract.FavColumns.TABLE_FAVORITE;

public class DbProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, FAVORITE);
        uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/#", FAVORITE_ID);
    }

    FavoriteHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new FavoriteHelper(getContext());
        dbHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = dbHelper.queryProvider(selection, selectionArgs);
                break;
            case FAVORITE_ID:
                cursor = dbHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                added = dbHelper.insertProvider(values);
                break;

            default:
                added = 0;
                break;
        }

        if(added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                Log.d("dbprovider delete", "deleted");
                deleted = dbHelper.deleteProvider(selection, selectionArgs);
                break;

            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int updated ;
        switch (uriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated =  dbHelper.updateProvider(selection, selectionArgs, values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
