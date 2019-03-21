package com.example.stillvalid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DetaileProduits extends AppCompatActivity {
    ImageView btn_menu;
    String id_produit, Id_user;
    SharedPreferences prefs,prefscontart;
    ImageView imageproduit;
    Context context;
    List<Post> postList = new ArrayList<>();
    TextView dureegrantie, txtdate, enseigne, nomproduit;
    public String url = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaile_produits);

        imageproduit = findViewById(R.id.profile_image);
        dureegrantie = findViewById(R.id.txtgarantie);
        txtdate = findViewById(R.id.txtDat);
        enseigne = findViewById(R.id.enseigne);
        nomproduit = findViewById(R.id.txtnom);

        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);

        String restoredid = prefs.getString("Id_Produit", null);

        if (restoredid != null) {
            id_produit = restoredid;
            loadproduit();
            Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
        }


        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DetaileProduits.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        loadproduit();
    }

    private void loadproduit() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_produit, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    nomproduit.setText(response.getJSONObject(0).getString("nom"));
                    dureegrantie.setText(response.getJSONObject(0).getString("garantie"));
                    txtdate.setText(response.getJSONObject(0).getString("dAchat"));
                    enseigne.setText(response.getJSONObject(0).getString("enseigne"));

                    Picasso.get()
                            .load(response.getJSONObject(0).getString("photo"))
                            .resize(400, 500)
                            .into(imageproduit);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    public void return_Mes_Produit(View view) {
        startActivity(new Intent(this, MesProduits.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void EditProduit(View view) {
        startActivity(new Intent(this, ModifierProduits.class));
    }

    public void btn_vente(View view) {
        startActivity(new Intent(this, DeposerAnonnce.class));
    }

}
