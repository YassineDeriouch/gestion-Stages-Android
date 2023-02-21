package com.example.myapplication.Entities;

import java.util.List;

public class Professeur {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    List<Etudiant> idE;

    public static int cpt=1;
    public Professeur() {
    }

    public Professeur(int id,String nom, String prenom, String email, String password,List<Etudiant> idE) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.idE = idE;
    }

    public Professeur(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Etudiant> getIdE() {
        return idE;
    }

    public void setIdE(List<Etudiant> idE) {
        this.idE = idE;
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
