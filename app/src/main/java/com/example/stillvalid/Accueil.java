package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear=calendar.get(calendar.YEAR);
        String str =Integer.toString(currentYear);
        TextView textView = findViewById(R.id.bas);
        textView.setText("COPYRGHIT StillValid All RIGHTS RESERVED - " + str );



    }
    public void mes_prod(View view) {
        startActivity(new Intent(this,MesProduits.class));
    }
    public void ajou_prod(View view) {
        startActivity(new Intent(this,Ajouter_Produits.class));
    }

    public void bou(View view) {
        startActivity(new Intent(this,Boutique.class));
    }
}
