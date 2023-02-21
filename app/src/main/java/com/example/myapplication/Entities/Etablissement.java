package com.example.myapplication.Entities;


public class Etablissement {

    private int idEtablissement;
    private String nom;
    private String description;
    private int idResponsable;
public static int cpt =1;

    public Etablissement() {

    }
    public Etablissement(int idEtablissement,String nom, String description, int idResponsable){
        this.nom = nom;
        this.description= description;
        this.idResponsable = idResponsable;
        this.idEtablissement=idEtablissement;
    }

    public int getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(int idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "idEtablissement=" + idEtablissement +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", idResponsable=" + idResponsable +
                '}';
    }


}
