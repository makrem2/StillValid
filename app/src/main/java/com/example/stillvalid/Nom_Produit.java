package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Nom_Produit extends AppCompatActivity {
    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom__produit);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Nom_Produit.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    public void valid_Article (View view){
        startActivity(new Intent(this,Date_achat.class));
    }

    public void vers_date_achat (View view){
        startActivity(new Intent(this,Date_achat.class));
    }

    public void return_marque_prod (View view){
        startActivity(new Intent(this,Marque_Produit.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

}
