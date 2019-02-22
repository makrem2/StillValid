package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Marque_Produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marque__produit);
    }
    public void valid_marque (View view){
        startActivity(new Intent(this,Nom_Produit.class));
    }

    public void return_ens_achat (View view){
        startActivity(new Intent(this,enseigne_achat.class));
    }

}

