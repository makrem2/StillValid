package com.example.stillvalid;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Recapulatif_contrat extends AppCompatActivity {
    ImageView btn_menu;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    String typecontrat, dateecheance;

    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Marque = new ArrayList<>();

    EditText Dateechance;
    Spinner Typecontrat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapulatif_contrat);

        Typecontrat = findViewById(R.id.edit_type);
        Dateechance = findViewById(R.id.edit_date_echance);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Recapulatif_contrat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        prefs = getSharedPreferences("ajoutercontart", MODE_PRIVATE);
        String restoredid = prefs.getString("typecontrat", null);
        String restoredidd = prefs.getString("dateecheance", null);
        if ((restoredid != null) || (restoredidd != null)) {
            typecontrat = restoredid;
            dateecheance = restoredidd;

            Dateechance.setText(dateecheance);
            //Typecontrat.setAdapter();

            //  Toast.makeText(this, id_annonce, Toast.LENGTH_SHORT).show();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.16/StillValid/GetALLTypes.php", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            List_Marque.add(response.getJSONObject(i).getString("libelle"));
                        }
                        Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                        Typecontrat.setAdapter(Adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Recapulatif_contrat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);


        }
    }


    public void return_ajou_ph_contrat(View view) {
        startActivity(new Intent(this, Ajouter_photo_contrat.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void recapulatifcontart(View view) {

        Toast.makeText(Recapulatif_contrat.this, dateecheance, Toast.LENGTH_SHORT).show();
        Toast.makeText(Recapulatif_contrat.this, typecontrat, Toast.LENGTH_SHORT).show();
    }
}
