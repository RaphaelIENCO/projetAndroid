package com.example.administrateur.projetandroid;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Avis.class}, version = 1)
public abstract class AppAvisbase extends RoomDatabase {

    private static volatile AppAvisbase INSTANCE=null;
    static AppAvisbase getAvisbase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppAvisbase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppAvisbase.class, "character_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AvisDAO avisDAO();

}
