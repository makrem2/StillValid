package com.example.stillvalid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Modifier_Annonce extends AppCompatActivity {
    Spinner categoriee;
    EditText titree, descriptionn, prixx, villee, telphone;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Categorie = new ArrayList<>();
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    ImageView image;

    public static final String ID_ANNONCE = "id_annonce";
    public static final String CATEGORIE = "categorie";
    public static final String TITRE = "titre";
    public static final String DESCRIPTION = "description";
    public static final String PRIX = "prix";
    public static final String VILLE = "ville";
    public static final String NUMTEL = "numtel";

    String categorie, titre, description, prix, ville, numtel, id_annonce;

    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier__annonce);

        image = findViewById(R.id.profile_image);
        categoriee = findViewById(R.id.sp_categorie);
        titree = findViewById(R.id.edit_Titre);
        descriptionn = findViewById(R.id.edit_description);
        prixx = findViewById(R.id.edit_prix);
        villee = findViewById(R.id.edit_ville);
        telphone = findViewById(R.id.edit_tel);


        prefs = getSharedPreferences("Boutique", MODE_PRIVATE);

        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, config.GetAllGATEGORIE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Categorie.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Categorie);
                    categoriee.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Modifier_Annonce.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(request2);


        String restoredid = prefs.getString("Id_Annonce", null);
        if (restoredid != null) {
            id_annonce = restoredid;
            //  Toast.makeText(this, id_annonce, Toast.LENGTH_SHORT).show();

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, config.BoutiqueById + id_annonce, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        titree.setText(response.getJSONObject(0).getString("titre"));
                        telphone.setText(response.getJSONObject(0).getString("numtel"));
                        categoriee.setSelection(TrouverIndice(response.getJSONObject(0).getString("categorie")));
                        villee.setText(response.getJSONObject(0).getString("ville"));
                        prixx.setText(response.getJSONObject(0).getString("prix"));
                        descriptionn.setText(response.getJSONObject(0).getString("description"));
                        Picasso.get()
                                .load(response.getJSONObject(0).getString("photoProduit"))
                                .resize(400, 500)
                                .into(image);


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
    }

    public void valid_modif_annonce(View view) {
        categorie = categoriee.getSelectedItem().toString();
        titre = titree.getText().toString().trim();
        description = descriptionn.getText().toString().trim();
        prix = prixx.getText().toString().trim();
        ville = villee.getText().toString().trim();
        numtel = telphone.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.Modif_AnnonceById, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(Modifier_Annonce.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Fiche_Produite.class));
                } else {
                    Toast.makeText(Modifier_Annonce.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Modifier_Annonce.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ID_ANNONCE, id_annonce);
                params.put(CATEGORIE, categorie);
                params.put(TITRE, titre);
                params.put(DESCRIPTION, description);
                params.put(PRIX, prix);
                params.put(VILLE, ville);
                params.put(NUMTEL, numtel);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public int TrouverIndice(String itemCt) {
        int i = 0;
        for (String item : List_Categorie) {
            if (item.equals(itemCt)) {
                return i;
            }
            i++;
        }
        return 0;
    }
}


