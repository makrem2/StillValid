package com.example.stillvalid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;

public class Modifier_Annonce extends AppCompatActivity {
    ImageView btn_menu;
    Spinner categorie;
    EditText titre,description,prix,ville,telphone;
    public static final String DATA_URL = "http://stillvalid/Spinner_categorire.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier__annonce);

        categorie = findViewById(R.id.sp_categorie);
        titre = findViewById(R.id.edit_Titre);
        description = findViewById(R.id.edit_description);
        prix = findViewById(R.id.edit_prix);
        ville = findViewById(R.id.edit_ville);
        telphone = findViewById(R.id.edit_tel);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Modifier_Annonce.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }




}
