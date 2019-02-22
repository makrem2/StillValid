package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class enseigne_achat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseigne_achat);
    }

    public void valid_enseigne_achat (View view){
        startActivity(new Intent(this,Marque_Produit.class));
    }
    public void return_ajout_prod (View view){
        startActivity(new Intent(this,Ajouter_Produits.class));
    }
}
