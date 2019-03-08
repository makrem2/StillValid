package com.example.stillvalid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Duree_garantie extends AppCompatActivity {
    ImageView btn_menu;
    EditText duree_garentie;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duree_garantie);
        duree_garentie = findViewById(R.id.sp_duree_garentie);

        prefs = getSharedPreferences("enseigneachat", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.img_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Duree_garantie.this, btn_menu);
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
                    duree_garentie.setText(listResult.get(0));
                }
                break;
        }
    }

    public void valid_duree_garantie(View view) {

        editors.putString("duree garentie", duree_garentie.getText().toString());
        editors.commit();
//      Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Ajouter_Photo_Produit.class);
        startActivity(intent);

    }

    public void return_ajou_date_achat(View view) {
        startActivity(new Intent(this, Date_achat.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void btn_efface(View view) {
        String Text = duree_garentie.getText().toString();
        if (Text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            duree_garentie.setText("");
        }


    }
}
