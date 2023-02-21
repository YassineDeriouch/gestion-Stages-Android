package com.example.myapplication.Entities;

import java.sql.Date;

public class Reunion {
    private int idReunion;
    private String libelle;
    private String salle;
    private String date;
    private int idP, idE;

    public Reunion(int idReunion, String libelle, String salle, String date, int idP, int idE) {
        this.idReunion = idReunion;
        this.libelle = libelle;
        this.salle = salle;
        this.date = date;
        this.idP = idP;
        this.idE = idE;
    }


    public Reunion() {
    }

    public int getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(int idReunion) {
        this.idReunion = idReunion;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    @Override
    public String toString() {
        return "Reunion{" +
                "idReunion=" + idReunion +
                ", libelle='" + libelle + '\'' +
                ", salle='" + salle + '\'' +
                ", date=" + date +
                ", idP=" + idP +
                ", idE=" + idE +
                '}';
    }
}
