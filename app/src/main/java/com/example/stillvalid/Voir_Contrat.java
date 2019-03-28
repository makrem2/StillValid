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

public class Voir_Contrat extends AppCompatActivity {
    SharedPreferences prefscontart;
    ImageView voircont;
    String id_contrat;
    public String url = "http://192.168.1.18/StillValid/ContratById.php?id_contrat=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir__contrat);

        voircont = findViewById(R.id.voirca);

        prefscontart = getSharedPreferences("mescontart", MODE_PRIVATE);
        String restoredid = prefscontart.getString("Id_Contrat", null);

        if (restoredid != null) {
            id_contrat = restoredid;
            Toast.makeText(this, id_contrat, Toast.LENGTH_SHORT).show();

            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url + id_contrat, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        Picasso.get()
                                .load(response.getJSONObject(0).getString("photo"))
                                .resize(400, 500)
                                .into(voircont);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue queue2 = Volley.newRequestQueue(this);
            queue2.add(request2);
        }


    }

    public void btnreturn(View view) {
        startActivity(new Intent(this, Detail_Contrat.class));
    }
}

