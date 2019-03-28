package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class enseigne_achat extends AppCompatActivity {
    ImageView btn_menu;
    EditText Enseigne;
    SharedPreferences prefs;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseigne_achat);


        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        Enseigne = findViewById(R.id.enseigne);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(enseigne_achat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

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
                    Enseigne.setText(listResult.get(0));
                }
                break;
        }
    }

    public void valid_enseigne_achat(View view) {
        String enseigne = Enseigne.getText().toString();
        if (!enseigne.isEmpty()) {
            editors.putString("Enseigne", enseigne);
            editors.apply();
            startActivity(new Intent(this, Marque_Produit.class));
        } else {
            Enseigne.setError("Champ obligatoire");
        }

    }

    public void return_ajout_prod(View view) {
        startActivity(new Intent(this, Ajouter_Produits.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void btn_efface(View view) {
        String Text = Enseigne.getText().toString();
        if (Text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            Enseigne.setText("");
        }

    }

    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }

    public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }

    public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }

    public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(enseigne_achat.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}
