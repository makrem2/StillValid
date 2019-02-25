package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class enseigne_achat extends AppCompatActivity {
    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseigne_achat);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(enseigne_achat.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    public void valid_enseigne_achat (View view){
        startActivity(new Intent(this,Marque_Produit.class));
    }
    public void return_ajout_prod (View view){
        startActivity(new Intent(this,Ajouter_Produits.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
}
