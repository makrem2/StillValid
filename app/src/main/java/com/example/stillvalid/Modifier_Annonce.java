package com.example.stillvalid;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    ArrayList<String> List_Marque = new ArrayList<>();
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    public static final String CATEGORIE = "categorie";
    public static final String TITRE = "titre";
    public static final String DESCRIPTION = "description";
    public static final String PRIX = "prix";
    public static final String VILLE = "ville";
    public static final String NUMTEL = "numtel";

    String categorie, titre, description, prix, ville, numtel, id_annonce;

    public static final String DATA_URL = "http://192.168.1.20/StillValid/GetALLMarque.php";
    public static final String Modif_URL = "http://192.168.1.20/StillValid/test.php?id_annonce=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier__annonce);

        categoriee = findViewById(R.id.sp_categorie);
        titree = findViewById(R.id.edit_Titre);
        descriptionn = findViewById(R.id.edit_description);
        prixx = findViewById(R.id.edit_prix);
        villee = findViewById(R.id.edit_ville);
        telphone = findViewById(R.id.edit_tel);

        prefs = getSharedPreferences("Boutique", MODE_PRIVATE);
        String restoredid = prefs.getString("Id_Annonce", null);
        if (restoredid != null) {
            id_annonce = restoredid;
            //  Toast.makeText(this, id_annonce, Toast.LENGTH_SHORT).show();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


    public void valid_modif_annonce(View view) {


        categorie = categoriee.getSelectedItem().toString();
        titre = titree.getText().toString().trim();
        description = descriptionn.getText().toString().trim();
        prix = prixx.getText().toString().trim();
        ville = villee.getText().toString().trim();
        numtel = telphone.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Modif_URL + id_annonce, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(Modifier_Annonce.this, response, Toast.LENGTH_LONG).show();
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
                params.put(CATEGORIE, categorie);
                params.put(TITRE, titre);
                params.put(DESCRIPTION, description);
                params.put(PRIX, prix);
                params.put(VILLE, ville);
                params.put(NUMTEL, numtel);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}


