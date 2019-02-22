package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Date_echence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_echence);
    }

    public void valid_echeance (View view){
        startActivity(new Intent(this,Ajouter_photo_contrat.class));
    }
    public void return_type_contrat (View view){
        startActivity(new Intent(this,Type_contrat.class));
    }
}
