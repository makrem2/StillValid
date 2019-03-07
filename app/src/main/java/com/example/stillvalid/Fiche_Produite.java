package com.example.stillvalid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class Fiche_Produite extends AppCompatActivity {
    String id_annonce;
    SharedPreferences prefs;
    ImageView imgproduit;
    Context context;
    TextView nomproduit, textdescription, telphone, textemail, textville, textprix;
    public String url = "http://192.168.1.21/StillValid/boutiqueById.php?id_annonce=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche__produite);

        context = this;
        imgproduit = findViewById(R.id.img_fiche_prod);
        nomproduit = findViewById(R.id.txt_nom_prod);
        textdescription = findViewById(R.id.txt_categorie);
        telphone = findViewById(R.id.num_tel);
        textemail = findViewById(R.id.txt_email);
        textville = findViewById(R.id.txt_ville_vendeur);
        textprix = findViewById(R.id.txt_prix_prod);

        prefs = getSharedPreferences("Boutique", MODE_PRIVATE);
        String restoredid = prefs.getString("Id_Annonce", null);
        if (restoredid != null) {
            id_annonce = restoredid;
            loadboutique();
            //  Toast.makeText(this, id_annonce, Toast.LENGTH_SHORT).show();
        }
    }
    private void loadboutique() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + id_annonce, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    nomproduit.setText(response.getString("titre"));
                    telphone.setText(response.getString("numtel"));
                    textemail.setText(response.getString("email"));
                    textville.setText(response.getString("ville"));
                    textprix.setText(response.getString("prix"));
                    textdescription.setText(response.getString("description"));
                    Picasso.get()
                            .load(response.getString("photoProduit"))
                            .resize(400, 500)
                            .into(imgproduit);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                if (List.get(0).getUser_id().equals(Id_user)) {
//                    editMenu.setVisibility(View.VISIBLE);
//                } else {
//                    editMenu.setVisibility(View.INVISIBLE);
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void btnreturn(View view) {
        startActivity(new Intent(this, Boutique.class));
    }
}

