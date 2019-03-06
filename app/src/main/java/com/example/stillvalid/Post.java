package com.example.stillvalid;

public class Post {
    String nom_produit;
    String lieu;
    String img_nom_produit;
    String prix;

    public Post() {
    }

    public Post(String nom_produit, String lieu, String img_nom_produit, String prix) {
        this.nom_produit = nom_produit;
        this.lieu = lieu;
        this.img_nom_produit = img_nom_produit;
        this.prix = prix;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImg_nom_produit() {
        return img_nom_produit;
    }

    public void setImg_nom_produit(String img_nom_produit) {
        this.img_nom_produit = img_nom_produit;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
