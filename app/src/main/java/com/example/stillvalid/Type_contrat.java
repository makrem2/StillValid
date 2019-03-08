package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;

public class Type_contrat extends AppCompatActivity {
    ImageView btn_menu;
    Spinner typecontrat;

    SharedPreferences prefs;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_contrat);

        typecontrat = findViewById(R.id.spinner2);

        prefs = getSharedPreferences("typecontart", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Type_contrat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    public void valid_contrat(View view) {
        editors.putString("duree garentie", typecontrat.getAdapter().toString());
        editors.commit();
//      Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Date_echence.class);
        startActivity(intent);


    }

    public void return_ajouterproduit(View view) {
        startActivity(new Intent(this, Ajouter_Produits.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }
}
