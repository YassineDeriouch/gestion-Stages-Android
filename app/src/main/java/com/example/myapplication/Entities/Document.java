package com.example.myapplication.Entities;

import java.sql.Date;
import java.util.Arrays;

public class Document {
    private int idDocument;
    private byte[] fichier;
    private java.util.Date dateEnvoi;
    private String fileName, fileType;

    private Etudiant etudiant;
    private Professeur professeur;
    private ResponsableStage responsableStage;

    public Document(int idDocument, byte[] fichier, Date dateEnvoi, String fileName, String fileType,
                    Etudiant etudiant, Professeur professeur, ResponsableStage responsableStage) {
        this.idDocument = idDocument;
        this.fichier = fichier;
        this.dateEnvoi = dateEnvoi;
        this.fileName = fileName;
        this.fileType = fileType;
        this.etudiant = etudiant;
        this.professeur = professeur;
        this.responsableStage = responsableStage;
    }

    public Document() {
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public java.util.Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public ResponsableStage getResponsableStage() {
        return responsableStage;
    }

    public void setResponsableStage(ResponsableStage responsableStage) {
        this.responsableStage = responsableStage;
    }

    @Override
    public String toString() {
        return "Document{" +
                "idDocument=" + idDocument +
                ", fichier=" + Arrays.toString(fichier) +
                ", dateEnvoi=" + dateEnvoi +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", etudiant=" + etudiant +
                ", professeur=" + professeur +
                ", responsableStage=" + responsableStage +
                '}';
    }
}
