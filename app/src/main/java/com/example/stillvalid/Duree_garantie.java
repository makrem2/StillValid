package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Duree_garantie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duree_garantie);
    }

    public void valid_garantie (View view){
        startActivity(new Intent(this,Ajouter_Photo_Produit.class));
    }
}
