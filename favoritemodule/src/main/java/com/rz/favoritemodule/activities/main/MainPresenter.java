package com.rz.favoritemodule.activities.main;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import static com.rz.favoritemodule.db.DbContract.FavColumns.CONTENT_URI;

public class MainPresenter implements MainContract.Presenter{

    private Context ctx;
    private MainContract.View view;


    MainPresenter(MainContract.View view, Context ctx){
        this.ctx = ctx;
        this.view = view;
    }

    @Override
    public void getFavoriteMovies() {
        new LoadFavoriteAsync().execute();
    }

    private class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor> {

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
            view.showNotFound(cursor.getCount() == 0);
        }
    }
}
