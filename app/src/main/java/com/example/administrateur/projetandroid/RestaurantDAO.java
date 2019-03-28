package com.example.administrateur.projetandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RestaurantDAO {
    @Query("SELECT * FROM restaurant")
    List<Restaurant> getAllRestaurant();

    @Insert
    void insertRestaurant(Restaurant... restaurants);
}
