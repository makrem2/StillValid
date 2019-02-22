package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Nom_Produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom__produit);
    }

    public void valid_Article (View view){
        startActivity(new Intent(this,Date_achat.class));
    }

    public void vers_date_achat (View view){
        startActivity(new Intent(this,Date_achat.class));
    }

    public void return_marque_prod (View view){
        startActivity(new Intent(this,Marque_Produit.class));
    }

}
