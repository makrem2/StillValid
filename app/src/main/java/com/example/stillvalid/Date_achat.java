package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Date_achat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_achat);
    }

    public void valid_date_achat (View view){
        startActivity(new Intent(this,Duree_garantie.class));
    }
    public void return_ajou_nom_prod (View view){
        startActivity(new Intent(this,Nom_Produit.class));
    }
}
