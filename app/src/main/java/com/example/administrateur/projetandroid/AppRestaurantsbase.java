package com.example.administrateur.projetandroid;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Restaurant.class}, version = 1)
public abstract class AppRestaurantsbase extends RoomDatabase {

    private static volatile AppRestaurantsbase INSTANCE=null;
    static AppRestaurantsbase getRestaurantsbase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRestaurantsbase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRestaurantsbase.class, "restaurants_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RestaurantDAO restaurantDAO();
}
