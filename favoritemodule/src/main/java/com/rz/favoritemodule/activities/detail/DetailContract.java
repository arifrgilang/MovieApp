package com.rz.favoritemodule.activities.detail;

import com.rz.favoritemodule.model.MovieObject;

public interface DetailContract {
    interface View{
        void setView(MovieObject results);
    }
}
