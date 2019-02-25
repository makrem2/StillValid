package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Ajouter_photo_contrat extends AppCompatActivity {
    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_photo_contrat);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Ajouter_photo_contrat.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }
    public void valid_photo_cont (View view){
        startActivity(new Intent(this,Recapulatif_contrat.class));
    }
    public void return_date_echance (View view){
        startActivity(new Intent(this,Date_echence.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
}
