package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Ajouter_Photo_Produit extends AppCompatActivity {
    ImageView btn_menu,prnd_photo_article,prnd_photo_facture;

//    SharedPreferences prefs;
//    SharedPreferences.Editor editors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__photo__produit);

        prnd_photo_article=findViewById(R.id.img_prender_article);
        prnd_photo_facture=findViewById(R.id.img_prender_facture);

//        prefs = getSharedPreferences("enseigneachat", MODE_PRIVATE);
//        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Ajouter_Photo_Produit.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }
    public void valid_photo (View view){
//        editors.putString("ImagePathfacture", prnd_photo_facture);
//        editors.commit();
//        editor.putString("ImagePath", selectedImagePath);
//
//        editor.commit();
////      Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(getApplicationContext(), recapitulatife_Produit.class);
//        startActivity(intent);

    }
    public void return_ajou_ph_prod (View view){
    startActivity(new Intent(this,Duree_garantie.class));}

    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
}
