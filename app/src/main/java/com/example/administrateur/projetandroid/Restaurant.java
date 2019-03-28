package com.example.administrateur.projetandroid;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "restaurant")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    private int rid;

    @ColumnInfo(name = "Nom")
    private String nom;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Ignore
    public Restaurant(int rid, String nom, double latitude, double longitude) {
        this.rid = rid;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurant(String nom, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                ", nom='" + nom + '\'' +
                '}';
    }
}
