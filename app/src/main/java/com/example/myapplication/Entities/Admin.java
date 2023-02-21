package com.example.myapplication.Entities;

public class Admin {
    private int idAdmin;
    private String email;
    private String password;

    public static int cpt=1;
    public Admin() {
        idAdmin = cpt;
        cpt++;
    }

    public Admin(String email, String password) {
        this.idAdmin = cpt; cpt++;
        this.email = email;
        this.password = password;
    }

    public int getIdCompte() {
        return idAdmin;
    }

    public void setIdCompte(int idAdmin) {
        this.idAdmin = idAdmin;
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


    @Override
    public String toString() {
        return "Admin{" +
                "idAdmin=" + idAdmin +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
