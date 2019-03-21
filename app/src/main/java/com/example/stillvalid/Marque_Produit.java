package com.example.stillvalid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Marque_Produit extends AppCompatActivity {
    ImageView btn_menu;
    Spinner marqueprod;
    ArrayAdapter<String> Adapter;
    List<marque> listMarques = new ArrayList<>();
    ArrayList<String> List_Marque = new ArrayList<>();
    ArrayList<String> sav = new ArrayList<>();
    SharedPreferences prefs, prefss;
    SharedPreferences.Editor editors, editorss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marque__produit);

        marqueprod = findViewById(R.id.sp_marque_prod);

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();

        prefss = getSharedPreferences("sav", MODE_PRIVATE);
        editorss = prefss.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Marque_Produit.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.18/StillValid/GetALLMarque.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                        //sav.add(response.getJSONObject(i).getString("sav"));
                        listMarques.add(new marque(response.getJSONObject(i).getString("id"),
                                response.getJSONObject(i).getString("libelle"),
                                response.getJSONObject(i).getString("sav"),
                                response.getJSONObject(i).getString("support")));

                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                    marqueprod.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Marque_Produit.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        marqueprod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editors.putString("Marque", Adapter.getItem(i));
                editors.apply();
                editorss.putString("marquesav", listMarques.get(i).getSav());
                editorss.apply();

                Toast.makeText(Marque_Produit.this, Adapter.getItem(i)+"", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void valid_marque(View view) {
        startActivity(new Intent(this, Nom_Produit.class));
    }

    public void return_ens_achat(View view) {
        startActivity(new Intent(this, enseigne_achat.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void btn_efface(View view) {
        marqueprod.setSelection(0);
    }
}

