package com.example.administrateur.projetandroid;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "avis")
public class Avis {

    @PrimaryKey(autoGenerate = true)
    private int aid;

    @ColumnInfo(name = "date")
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Ignore
    public Avis(int aid, Date date, int note, String avis) {
        this.aid = aid;
        this.date = date;
        this.note = note;
        this.avis = avis;
    }

    public Avis(Date date, int note, String avis) {
        this.date = date;
        this.note = note;
        this.avis = avis;
    }
}
