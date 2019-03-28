package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class Voir_Facture extends AppCompatActivity {
    ImageView img_fact;
    SharedPreferences prefs;
    String id_produit;
    public String voirfacture = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir__facture);

        img_fact = findViewById(R.id.img_facture);

        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);
        String restoredid = prefs.getString("Id_Produit", null);

        if (restoredid != null) {
            id_produit = restoredid;
            Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, voirfacture + id_produit, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        Picasso.get()
                                .load(response.getJSONObject(0).getString("facture"))
                                .resize(400, 500)
                                .into(img_fact);


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

    public void btnreturn(View view) {
        startActivity(new Intent(this, DetaileProduits.class));
    }
}
