package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ajouter_Produits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__produits);
    }

    public void ajou_produit (View view){
        startActivity(new Intent(this,enseigne_achat.class));
    }

    public void ajou_contrat (View view){
        startActivity(new Intent(this,Type_contrat.class));
    }
}
