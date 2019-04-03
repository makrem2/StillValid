package com.example.stillvalid;

public class Post {
    int id;
    private int idUser;
    private String nom_produit;
    private String lieu;
    private String prix;
    private String img_nom_produit;


    public Post() {
    }

    public Post(int id, int idUser, String nom_produit, String lieu, String img_nom_produit, String prix) {
        this.id = id;
        this.idUser = idUser;
        this.nom_produit = nom_produit;
        this.lieu = lieu;
        this.img_nom_produit = img_nom_produit;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
