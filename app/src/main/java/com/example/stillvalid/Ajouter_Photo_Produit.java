package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ajouter_Photo_Produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__photo__produit);
    }
    public void valid_photo (View view){
        startActivity(new Intent(this,recapitulatife_Produit.class));
    }
}
