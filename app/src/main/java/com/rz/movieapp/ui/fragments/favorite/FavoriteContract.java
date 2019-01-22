package com.rz.movieapp.ui.fragments.favorite;

import android.content.Context;
import android.database.Cursor;

public interface FavoriteContract {
    interface View{
        void showLoading(Boolean condition);
        void showNotFound(Boolean condition);
        void setView(Cursor results);
    }
    interface Presenter{
        void getFavoriteMovies();
        void setContext(Context ctx);
    }
}
