package com.rz.movieapp.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDb extends RoomDatabase {
    public abstract MovieDao movie();
    private static MovieDb sInstance;

    public static synchronized MovieDb getInstance(Context context){
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MovieDb.class, "ex")
                    .build();
//            sInstance.populateInitialData();
        }
        return sInstance;
    }
}
