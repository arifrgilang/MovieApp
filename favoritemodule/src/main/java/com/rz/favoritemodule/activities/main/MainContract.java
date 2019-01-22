package com.rz.favoritemodule.activities.main;

import android.database.Cursor;

public interface MainContract {
    interface View{
        void showLoading(Boolean condition);
        void showNotFound(Boolean condition);
        void setView(Cursor results);
    }
    interface Presenter{
        void getFavoriteMovies();
    }
}
