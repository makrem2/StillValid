package com.example.stillvalid;

public class PostProduit {
    int id_prod;
    String txt_prod;
    String txt_duree;
    String img_prod;
    String txt;


    public PostProduit() {
    }

    public PostProduit(int id_prod, String txt_prod, String txt_duree, String img_prod, String txt) {
        this.id_prod = id_prod;
        this.txt_prod = txt_prod;
        this.txt_duree = txt_duree;
        this.img_prod = img_prod;
        this.txt = txt;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getTxt_prod() {
        return txt_prod;
    }

    public void setTxt_prod(String txt_prod) {
        this.txt_prod = txt_prod;
    }

    public String getTxt_duree() {
        return txt_duree;
    }

    public void setTxt_duree(String txt_duree) {
        this.txt_duree = txt_duree;
    }

    public String getImg_prod() {
        return img_prod;
    }

    public void setImg_prod(String img_prod) {
        this.img_prod = img_prod;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}