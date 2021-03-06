package com.example.stillvalid;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class DeposerAnonnce extends AppCompatActivity {
    String description_anc, categorie_anc, titre_anc, prix_anc, ville_anc, numtel_anc, email_anc, id_produit, Id_user, photo_produit;
    ImageView btn_menu;
    ProgressDialog progressDialog;
    Spinner categorie;
    EditText annonce, description_annonce, prix, ville, email, tel;
    ImageView photoprod;
    SharedPreferences prefs, prefss, prefsphotoprod;
    ArrayAdapter<String> Adapter;
    Config config;
    ArrayList<String> List_Categorie = new ArrayList<>();
    public static final String CATEGORIE = "categorie";
    public static final String DESCRIPTION = "description";
    public static final String TITRE = "titre";
    public static final String PRIX = "prix";
    public static final String USER_ID = "user_id";
    public static final String VILLE = "ville";
    public static final String TELEPHONE = "numtel";
    public static final String EMAIL = "email";
    public static final String PHOTOPRODUIT = "photoProduit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer_anonnce);


        categorie = findViewById(R.id.edit_categorie);
        annonce = findViewById(R.id.edit_annonce);
        description_annonce = findViewById(R.id.edit_annonce);
        prix = findViewById(R.id.editprix);
        ville = findViewById(R.id.edit_ville);
        email = findViewById(R.id.edit_email);
        tel = findViewById(R.id.btn_tel);
        photoprod = findViewById(R.id.still_valid1);
        btn_menu = findViewById(R.id.menu);

        prefs = getSharedPreferences("login", MODE_PRIVATE);

        Id_user = prefs.getString("id", null);


        prefss = getSharedPreferences("mesproduit", MODE_PRIVATE);

        String restoredid = prefss.getString("Id_Produit", null);

        prefsphotoprod = getSharedPreferences("photo_produit", MODE_PRIVATE);

        photo_produit = prefsphotoprod.getString("photoprod", null);

        if (restoredid != null) {
            id_produit = restoredid;
            loadproduit();
        }

        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, config.GetAllGATEGORIE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Categorie.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Categorie);
                    categorie.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeposerAnonnce.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(request2);
        categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorie_anc = Adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DeposerAnonnce.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    private void loadproduit() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, config.ProduitById + id_produit, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Picasso.get()
                            .load(response.getJSONObject(0).getString("photo"))
                            .resize(400, 500)
                            .into(photoprod);

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

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    private boolean Valider() {
        boolean valide = true;
        if (description_anc.isEmpty()) {
            description_annonce.setError("champs_obligatoir");
            valide = false;
        }
        if (titre_anc.isEmpty()) {
            annonce.setError("champs_obligatoir");
            valide = false;
        }
        if (prix_anc.isEmpty()) {
            prix.setError("champs_obligatoir");
            valide = false;
        }
        if (ville_anc.isEmpty()) {
            ville.setError("champs_obligatoir");
            valide = false;
        }
        if (numtel_anc.isEmpty()) {
            tel.setError("champs_obligatoir");
            valide = false;
        }
        if (email_anc.isEmpty()) {
            email.setError("champs_obligatoir");
            valide = false;
        }
        return valide;
    }

    public void valid_Deposer_annonce(View view) {
        description_anc = description_annonce.getText().toString().trim();
        titre_anc = annonce.getText().toString().trim();
        prix_anc = prix.getText().toString().trim();
        ville_anc = ville.getText().toString().trim();
        numtel_anc = tel.getText().toString().trim();
        email_anc = email.getText().toString().trim();
        if (Valider()) {
            RequestQueue requestQueue = Volley.newRequestQueue(DeposerAnonnce.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.Deposer_annonce, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()) {
                        Log.i("Myresponse", "" + response);
                        Toast.makeText(DeposerAnonnce.this, "" + response, Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(DeposerAnonnce.this, "errr", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Mysmart", "" + error);
                    Toast.makeText(DeposerAnonnce.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put(CATEGORIE, categorie_anc);
                    param.put(DESCRIPTION, description_anc);
                    param.put(TITRE, titre_anc);
                    param.put(USER_ID, Id_user);
                    param.put(VILLE, ville_anc);
                    param.put(TELEPHONE, numtel_anc);
                    param.put(PRIX, prix_anc);
                    param.put(EMAIL, email_anc);
                    param.put(PHOTOPRODUIT, photo_produit);
                    return param;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    public void btn_efface(View view) {
        annonce.setText("");
        description_annonce.setText("");
        prix.setText("");
        ville.setText("");
        email.setText("");
        tel.setText("");
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
        progressDialog = new ProgressDialog(DeposerAnonnce.this);
        progressDialog.setMessage("DECONNEXION");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }
}