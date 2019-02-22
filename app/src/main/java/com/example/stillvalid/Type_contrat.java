package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Type_contrat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_contrat);

    }
    public void valid_contrat (View view){
        startActivity(new Intent(this,Date_echence.class));
    }
    public void return_ajouterproduit (View view){
        startActivity(new Intent(this,Ajouter_Produits.class));
    }
}
