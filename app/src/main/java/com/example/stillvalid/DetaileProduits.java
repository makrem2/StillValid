package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class DetaileProduits extends AppCompatActivity {
    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaile_produits);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DetaileProduits.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });


    }
    public void  return_Mes_Produit (View view){
        startActivity(new Intent(this,MesProduits.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

    public void  EditProduit (View view){
        startActivity(new Intent(this,ModifierProduits.class));}

    public void btn_vente (View view){
        startActivity(new Intent(this,DeposerAnonnce.class));}

}
