package com.example.administrateur.projetandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AvisDAO {
    @Query("SELECT * FROM avis")
    List<Avis> getAllAvis();

    @Insert
    void insertAvis(Avis... avis);
}
