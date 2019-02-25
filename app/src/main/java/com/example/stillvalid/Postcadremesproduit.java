package com.example.stillvalid;

public class Postcadremesproduit {
    String txt_nom_contrat;
    String txt_duree_contrat;
    String img_contrat;

    public Postcadremesproduit() {
    }

    public Postcadremesproduit(String txt_nom_contrat, String txt_duree_contrat, String img_contrat) {
        this.txt_nom_contrat = txt_nom_contrat;
        this.txt_duree_contrat = txt_duree_contrat;
        this.img_contrat = img_contrat;
    }

    public String getTxt_nom_contrat() {
        return txt_nom_contrat;
    }

    public void setTxt_nom_contrat(String txt_nom_contrat) {
        this.txt_nom_contrat = txt_nom_contrat;
    }

    public String getTxt_duree_contrat() {
        return txt_duree_contrat;
    }

    public void setTxt_duree_contrat(String txt_duree_contrat) {
        this.txt_duree_contrat = txt_duree_contrat;
    }

    public String getImg_contrat() {
        return img_contrat;
    }

    public void setImg_contrat(String img_contrat) {
        this.img_contrat = img_contrat;
    }

}
