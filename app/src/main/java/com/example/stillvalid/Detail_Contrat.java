package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Detail_Contrat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__contrat);
    }
    public void  bn_return_Mes_Produit (View view){
        startActivity(new Intent(this,MesProduits.class));
    }
}
