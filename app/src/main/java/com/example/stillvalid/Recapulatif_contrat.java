package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Recapulatif_contrat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapulatif_contrat);
    }

    public void return_ajou_ph_contrat (View view){
        startActivity(new Intent(this,Ajouter_photo_contrat.class));
    }
}
