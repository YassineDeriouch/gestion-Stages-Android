package com.example.myapplication.Entities;

public class Etudiant {
    
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String nomFiliere;
    private int nbniveau;

    public Etudiant() {
    }

    public Etudiant(int id, String nom, String prenom, String email, String password, String nomFiliere, int nbniveau) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.nomFiliere = nomFiliere;
        this.nbniveau = nbniveau;
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

    public String getNomFiliere() {
        return nomFiliere;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public int getNbniveau() {
        return nbniveau;
    }

    public void setNbniveau(int nbniveau) {
        this.nbniveau = nbniveau;
    }


    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nomFiliere='" + nomFiliere + '\'' +
                ", nbniveau=" + nbniveau +
                '}';
    }
}
