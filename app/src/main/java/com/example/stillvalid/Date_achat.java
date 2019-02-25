package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Date_achat extends AppCompatActivity {

    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_achat);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Date_achat.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    public void valid_date_achat (View view){
        startActivity(new Intent(this,Duree_garantie.class));
    }
    public void return_ajou_nom_prod (View view){
        startActivity(new Intent(this,Nom_Produit.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
}
