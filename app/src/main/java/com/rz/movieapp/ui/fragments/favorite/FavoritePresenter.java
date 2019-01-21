package com.rz.movieapp.ui.fragments.favorite;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.rz.movieapp.data.api.MovieDBClient;
import com.rz.movieapp.ui.fragments.nowplaying.NowPlayingContract;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;

import static com.rz.movieapp.db.DbContract.FavColumns.CONTENT_URI;

@Singleton
public class FavoritePresenter implements FavoriteContract.Presenter{
    private final String TAG = "FavoritePresenter";

    private Context ctx;
    private FavoriteContract.View view;

    @Inject
    FavoritePresenter(FavoriteContract.View view){
        this.view = view;
    }

    @Override
    public void getFavoriteMovies() {
        new LoadFavoriteAsync().execute();
    }

    @Override
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    private class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoading(true);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return ctx.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            view.showLoading(false);
            view.setView(cursor);
//            if(cursor.getCount() != null){
//
//            }
//            view.showNotFound(cursor.getCount() == 0);
        }
    }
}
