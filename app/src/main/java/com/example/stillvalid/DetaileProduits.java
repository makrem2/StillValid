package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetaileProduits extends AppCompatActivity {
    ImageView btn_menu, check, menuItem;
    String id_produit, SAV, marque;
    SharedPreferences prefs, prefscontart;
    CircleImageView imageproduit;
    int jours;
    ProgressDialog progressDialog;
    List<Post> postList = new ArrayList<>();
    TextView dureegrantie, txtdate, enseigne, nomproduit;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaile_produits);

        imageproduit = findViewById(R.id.profile_image);
        check = findViewById(R.id.imageView7);
        dureegrantie = findViewById(R.id.txtgarantie);
        txtdate = findViewById(R.id.txtDat);
        enseigne = findViewById(R.id.enseigne);
        nomproduit = findViewById(R.id.txtnom);
        menuItem = findViewById(R.id.menuItem);

        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);

        String restoredid = prefs.getString("Id_Produit", null);
        String restoredjour = prefs.getString("Nb_jours", null);
        //dureegrantie.setText(restoredjour);
        if (restoredjour != null) {

            jours = Integer.parseInt(restoredjour);


            if (jours <= 0) {
                imageproduit.setBorderColor(Color.RED);
                check.setImageResource(R.drawable.x);
            } else {
                imageproduit.setBorderColor(Color.parseColor("#358c42"));
                check.setImageResource(R.drawable.check_produits);
            }
            //Toast.makeText(this, jours + "", Toast.LENGTH_SHORT).show();
        }
        if (restoredid != null) {
            id_produit = restoredid;
            loadproduit();
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Config.ProduitById + id_produit, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    nomproduit.setText(response.getJSONObject(0).getString("nom"));
                    dureegrantie.setText(response.getJSONObject(0).getString("garantie"));
                    txtdate.setText(response.getJSONObject(0).getString("dAchat"));
                    enseigne.setText(response.getJSONObject(0).getString("enseigne"));
                    SAV = response.getJSONObject(0).getString("sav");
                    marque = response.getJSONObject(0).getString("marque");
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
        PopupMenu popupMenu = new PopupMenu(DetaileProduits.this, menuItem);
        popupMenu.getMenuInflater().inflate(R.menu.menu_ficheproduit, popupMenu.getMenu());
        popupMenu.show();

        //startActivity(new Intent(this, ModifierProduits.class));
    }


    public void annonce(View view) {

        startActivity(new Intent(this, DeposerAnonnce.class));
    }

    public void VoirFacture(View view) {

        startActivity(new Intent(this, Voir_Facture.class));
    }

    public void getSav(View view) {
        if (!SAV.isEmpty()) {

            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + SAV));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Il n'a pas de SAV disponible!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSupport(View view) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, config.GetSav + marque, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        String support = String.valueOf(response.getJSONObject(0).get("support"));
//                        Toast.makeText(context, ""+test, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(support));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Il n'a pas de Notice disponible!", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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
        progressDialog = new ProgressDialog(DetaileProduits.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }

    public void modifer(MenuItem item) {
        startActivity(new Intent(this, ModifierProduits.class));
    }

    public void supprimer(MenuItem item) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, config.SUPPRIMER_produitById + id_produit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(DetaileProduits.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MesProduits.class));
                } else {
                    Toast.makeText(DetaileProduits.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetaileProduits.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
