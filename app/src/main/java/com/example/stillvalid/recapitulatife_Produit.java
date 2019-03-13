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
import android.widget.TextView;
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

public class recapitulatife_Produit extends AppCompatActivity {
    EditText enseigneachatt,produitt,dateeachat,dureeegarantie;
    TextView btn_menu;
    SharedPreferences prefs;
    String Enseigne,Marque,Produit,Dateeachat,Dureegrantie ;
    Spinner marquee;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Marque = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatife__produit);

        enseigneachatt = findViewById(R.id.edit_enseigne_achat);
        produitt = findViewById(R.id.edit_produit);
        dateeachat = findViewById(R.id.edit_date_achat);
        dureeegarantie = findViewById(R.id.edit_duree_garentie);
        marquee = findViewById(R.id.edit_marque);

        btn_menu = findViewById(R.id.txt_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(recapitulatife_Produit.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        String enseigne = prefs.getString("Enseigne", null);
        enseigneachatt.setText(enseigne);
        String Produit = prefs.getString("Nom_Produit", null);
        produitt.setText(Produit);
        String Dateeachat = prefs.getString("dateachat", null);
        dateeachat.setText(Dateeachat);
        String Dureegrantie = prefs.getString("duree garentie", null);
        dureeegarantie.setText(Dureegrantie);



        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.17/StillValid/GetALLMarque.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, List_Marque);
                    marquee.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recapitulatife_Produit.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);



        }





    public void return_ajou_ph_prod (View view){
        startActivity(new Intent(this,Ajouter_Photo_Produit.class));
    }
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

}
