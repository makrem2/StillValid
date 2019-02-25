package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class recapitulatife_Produit extends AppCompatActivity {

    TextView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatife__produit);
        btn_menu = findViewById(R.id.txt_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(recapitulatife_Produit.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    public void return_ajou_ph_prod (View view){
        startActivity(new Intent(this,Ajouter_Photo_Produit.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

}
