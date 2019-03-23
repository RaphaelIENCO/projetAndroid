package com.example.administrateur.projetandroid;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "avis")
public class Avis {

    @PrimaryKey(autoGenerate = true)
    private int aid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "note")
    private int note;

    @ColumnInfo(name = "avis")
    private String avis;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Ignore
    public Avis(int aid, String username, String date, int note, String avis) {
        this.aid = aid;
        this.username = username;
        this.date = date;
        this.note = note;
        this.avis = avis;
    }

    public Avis(String username, String date, int note, String avis) {
        this.username = username;
        this.date = date;
        this.note = note;
        this.avis = avis;
    }
}
