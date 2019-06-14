package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class Fiche_Produite extends AppCompatActivity {
    String id_annonce, Id_user;
    SharedPreferences prefs, prefs2;
    ImageView imgproduit;
    Context context;
    Config config;
    ImageView editbnt;
    List<Post> postList = new ArrayList<>();
    TextView nomproduit, textdescription, telphone, textemail, textville, textprix;
    String telphonee;
    Button contactervendeur;

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
        editbnt = findViewById(R.id.editbnt);
        contactervendeur = findViewById(R.id.btn_contacter_le_vendeur);

        prefs = getSharedPreferences("Boutique", MODE_PRIVATE);

        prefs2 = getSharedPreferences("login", MODE_PRIVATE);

        String restoredid = prefs.getString("Id_Annonce", null);
        String restoredid_user = prefs2.getString("id", null);

        //Toast.makeText(this, restoredid_user, Toast.LENGTH_SHORT).show();
        if (restoredid_user != null) {
            Id_user = restoredid_user;
            //Toast.makeText(this, Id_user, Toast.LENGTH_SHORT).show();
        }
        if (restoredid != null) {
            id_annonce = restoredid;
            loadboutique();
            //Toast.makeText(this, id_annonce, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadboutique() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, config.BoutiqueById + id_annonce, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    nomproduit.setText(response.getJSONObject(0).getString("titre"));
                    telphone.setText(response.getJSONObject(0).getString("numtel"));
                    textemail.setText(response.getJSONObject(0).getString("email"));
                    textville.setText(response.getJSONObject(0).getString("ville"));
                    textprix.setText(response.getJSONObject(0).getString("prix"));
                    textdescription.setText(response.getJSONObject(0).getString("description"));

                    Picasso.get()
                            .load(response.getJSONObject(0).getString("photoProduit"))
                            .resize(400, 500)
                            .into(imgproduit);

                    if (Id_user.equals(response.getJSONObject(0).getString("user_id"))) {
                        editbnt.setVisibility(View.VISIBLE);
                        contactervendeur.setVisibility(View.INVISIBLE);

                    } else {
                        editbnt.setVisibility(View.INVISIBLE);
                        contactervendeur.setVisibility(View.VISIBLE);

                    }
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

    public void btnreturn(View view) {
        startActivity(new Intent(this, Boutique.class));
    }

    public void btn_edit(View view) {
        PopupMenu popupMenu = new PopupMenu(Fiche_Produite.this, editbnt);
        popupMenu.getMenuInflater().inflate(R.menu.menu_ficheproduit, popupMenu.getMenu());
        popupMenu.show();
    }

    public void modifer(MenuItem item) {
        startActivity(new Intent(this, Modifier_Annonce.class));
    }

    public void supprimer(MenuItem item) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, config.SUPPRIMER_BoutiqueById + id_annonce, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(Fiche_Produite.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Boutique.class));
                } else {
                    Toast.makeText(Fiche_Produite.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Fiche_Produite.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void contacter_vendeur(View view) {
        telphonee = telphone.getText().toString().trim();
        if (!telphonee.isEmpty()) {

            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + telphonee));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Il n'a pas de telphone disponible!", Toast.LENGTH_SHORT).show();
        }


    }
}
