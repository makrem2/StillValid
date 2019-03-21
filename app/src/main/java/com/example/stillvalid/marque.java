package com.example.stillvalid;

public class marque {


    String id;
    String libelle;
    String sav;
    String support;


    public marque() {
    }

    public marque(String id, String libelle, String sav, String support) {
        this.id = id;
        this.libelle = libelle;
        this.sav = sav;
        this.support = support;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSav() {
        return sav;
    }

    public void setSav(String sav) {
        this.sav = sav;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
