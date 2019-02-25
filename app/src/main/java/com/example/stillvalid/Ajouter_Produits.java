package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Ajouter_Produits extends AppCompatActivity {

    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__produits);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Ajouter_Produits.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    public void ajou_produit (View view){
        startActivity(new Intent(this,enseigne_achat.class));
    }

    public void ajou_contrat (View view){
        startActivity(new Intent(this,Type_contrat.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
}
