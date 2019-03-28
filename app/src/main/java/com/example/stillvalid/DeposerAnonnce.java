package com.example.stillvalid;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeposerAnonnce extends AppCompatActivity {
    String description_anc, categorie_anc, titre_anc, prix_anc, photo,ville_anc, numtel_anc, email_anc;
    ImageView btn_menu;
    ProgressDialog progressDialog;
    Spinner categorie;
    EditText annonce, description_annonce, prix, ville, email, tel;
    ImageView photoprod;
    String id_produit, Id_user, photo_produit;
    SharedPreferences prefs, prefss, prefsphotoprod;
    ArrayAdapter<String> Adapter;
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

    public static final String DATA_URL = "http://192.168.1.18/StillValid/GetALLCategorie.php";
    public String url = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";
    public String Deposer_annonce = "http://192.168.1.18/StillValid/DeposeAnnonce.php";

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
        //Toast.makeText(this, Id_user, Toast.LENGTH_SHORT).show();
        prefss = getSharedPreferences("mesproduit", MODE_PRIVATE);

        String restoredid = prefss.getString("Id_Produit", null);

        prefsphotoprod = getSharedPreferences("photo_produit", MODE_PRIVATE);

        photo_produit = prefsphotoprod.getString("photoprod", null);

        if (restoredid != null) {
            id_produit = restoredid;
            loadproduit();
            //Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
        }

        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONArray>() {
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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_produit, null, new Response.Listener<JSONArray>() {
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

    public void valid_Deposer_annonce(View view) {
        description_anc = description_annonce.getText().toString().trim();
        titre_anc = annonce.getText().toString().trim();
        prix_anc = prix.getText().toString().trim();
        ville_anc = ville.getText().toString().trim();
        numtel_anc = tel.getText().toString().trim();
        email_anc = email.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(DeposerAnonnce.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Deposer_annonce, new Response.Listener<String>() {
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

    public void btn_efface(View view) {
        String txtannonce = annonce.getText().toString();
        String txt_desc_ann = description_annonce.getText().toString();
        String txt_prix = prix.getText().toString();
        String txt_ville = ville.getText().toString();
        String txt_email = email.getText().toString();
        if (txtannonce.isEmpty() && (txt_desc_ann.isEmpty()) && (txt_prix.isEmpty()) && (txt_ville.isEmpty()) && (txt_email.isEmpty())) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            annonce.setText("");
            description_annonce.setText("");
            prix.setText("");
            ville.setText("");
            email.setText("");
        }
    }
    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(DeposerAnonnce.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}