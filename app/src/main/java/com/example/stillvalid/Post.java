package com.example.stillvalid;

public class Post {
    String txt_nom_produit;
    String txt_lieu_produit;
    String img_nom_produit;
    String  txt_prix_produit;

    public Post() {
    }

    public Post(String txt_nom_produit, String txt_lieu_produit, String txt_prix_produit,String img_nom_produit) {
        this.txt_nom_produit = txt_nom_produit;
        this.txt_lieu_produit = txt_lieu_produit;
        this.img_nom_produit = img_nom_produit;
        this.txt_prix_produit = txt_prix_produit;
    }

    public Post(String txt_nom_produit, String txt_lieu_produit, String img_nom_produit) {
        this.txt_nom_produit = txt_nom_produit;
        this.txt_lieu_produit = txt_lieu_produit;
        this.img_nom_produit = img_nom_produit;
        this.txt_prix_produit = txt_prix_produit;
    }

    public String getTxt_nom_produit() {
        return txt_nom_produit;
    }

    public void setTxt_nom_produit(String txt_nom_produit) {
        this.txt_nom_produit = txt_nom_produit;
    }

    public String getTxt_lieu_produit() {
        return txt_lieu_produit;
    }

    public void setTxt_lieu_produit(String txt_lieu_produit) {
        this.txt_lieu_produit = txt_lieu_produit;
    }

    public String getImg_nom_produit() {
        return img_nom_produit;
    }

    public void setImg_nom_produit(String img_nom_produit) {
        this.img_nom_produit = img_nom_produit;
    }

    public String getTxt_prix_produit() {
        return txt_prix_produit;
    }

    public void setTxt_prix_produit(String txt_prix_produit) {
        this.txt_prix_produit = txt_prix_produit;
    }
}
