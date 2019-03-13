package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class Type_contrat extends AppCompatActivity {
    ImageView btn_menu;
    Spinner typecontrat;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Type = new ArrayList<>();
    SharedPreferences prefs;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_contrat);

        typecontrat = findViewById(R.id.spinner2);
        prefs = getSharedPreferences("ajoutercontart", MODE_PRIVATE);
        editors = prefs.edit();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.16/StillValid/GetALLTypes.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Type.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, List_Type);
                    typecontrat.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Type_contrat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        typecontrat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editors.putString("typecontrat", typecontrat.getAdapter().toString());
                editors.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
