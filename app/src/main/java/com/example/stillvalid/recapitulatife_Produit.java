package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class recapitulatife_Produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatife__produit);
    }
    public void valid_recap (View view){
        startActivity(new Intent(this,Boutique.class));
    }
    public void return_ajou_ph_prod (View view){
        startActivity(new Intent(this,Ajouter_Photo_Produit.class));
    }

}
