package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

public class DeposerAnonnce extends AppCompatActivity {
    ImageView btn_menu;
    Spinner categorie;
    EditText annonce, description_annonce, prix, ville, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer_anonnce);

        categorie = (Spinner) findViewById(R.id.edit_categorie);
        annonce = (EditText) findViewById(R.id.edit_annonce);
        description_annonce = (EditText) findViewById(R.id.edit_annonce);
        prix = (EditText) findViewById(R.id.edit_prix);
        ville = (EditText) findViewById(R.id.edit_ville);
        email = (EditText) findViewById(R.id.edit_email);


        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DeposerAnonnce.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void btn_efface(View view) {
        String txtannonce = annonce.getText().toString();
        String txt_desc_ann = description_annonce.getText().toString();
        String txt_prix = prix.getText().toString();
        String txt_ville = ville.getText().toString();
        String txt_email = email.getText().toString();
        if (txtannonce.isEmpty() || (txt_desc_ann.isEmpty()) || (txt_prix.isEmpty()) || (txt_ville.isEmpty()) || (txt_email.isEmpty())) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            annonce.setText("");
            description_annonce.setText("");
            prix.setText("");
            ville.setText("");
            email.setText("");
        }
    }
}