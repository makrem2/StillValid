package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ajouter_photo_contrat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_photo_contrat);
    }
    public void valid_photo_cont (View view){
        startActivity(new Intent(this,Recapulatif_contrat.class));
    }
    public void return_date_echance (View view){
        startActivity(new Intent(this,Date_echence.class));
    }
}
