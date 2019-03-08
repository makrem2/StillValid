package com.example.stillvalid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Marque_Produit extends AppCompatActivity {
    ImageView btn_menu;
    Spinner marqueprod;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marque__produit);

        marqueprod = findViewById(R.id.sp_marque_prod);


        prefs = getSharedPreferences("enseigneachat", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Marque_Produit.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }
    public void valid_marque (View view){
        editors.putString("marqueprod", marqueprod.getAdapter().toString());
        editors.commit();
//      Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Nom_Produit.class);
        startActivity(intent);

    }

    public void return_ens_achat (View view){
        startActivity(new Intent(this,enseigne_achat.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

    public void vocale(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Sorry! Your device doesn't support speech language!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> listResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //marqueprod.setAdapter(listResult.get(0));
                }
                break;
        }
    }

    public void btn_efface(View view) {
        String Text = marqueprod.getContext().toString();
        if (Text.isEmpty()){
            Toast.makeText(getApplicationContext(),"Already Empty!!!",Toast.LENGTH_SHORT);
        }else{
            marqueprod.setAdapter(null);
        }


    }
}

