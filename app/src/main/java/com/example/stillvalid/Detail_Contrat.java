package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Detail_Contrat extends AppCompatActivity {
    ImageView btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__contrat);
        btn_menu = findViewById(R.id.img_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Detail_Contrat.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }
    public void  bn_return_Mes_Produit (View view){
        startActivity(new Intent(this,MesProduits.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

    public void EditContrat (View view){
        startActivity(new Intent(this,modifier_contrat.class));}
}
