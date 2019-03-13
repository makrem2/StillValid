package com.example.stillvalid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class Fiche_Produite extends AppCompatActivity {
    String id_annonce;
    SharedPreferences prefs;
    ImageView imgproduit;
    Context context;
    ImageView editbnt;
    TextView nomproduit, textdescription, telphone, textemail, textville, textprix;
    //TextView Id_user;
    public String url = "http://192.168.1.20/StillValid/boutiqueById.php?id_annonce=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche__produite);
        context = this;

        //Id_user = findViewById(R.id.txt_user_id);
        imgproduit = findViewById(R.id.img_fiche_prod);
        nomproduit = findViewById(R.id.txt_nom_prod);
        textdescription = findViewById(R.id.txt_categorie);
        telphone = findViewById(R.id.num_tel);
        textemail = findViewById(R.id.txt_email);
        textville = findViewById(R.id.txt_ville_vendeur);
        textprix = findViewById(R.id.txt_prix_prod);
        editbnt = findViewById(R.id.editbnt);

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
                    //Id_user.setText(response.getInt("user_id"));

                    Picasso.get()
                            .load(response.getString("photoProduit"))
                            .resize(400, 500)
                            .into(imgproduit);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                if (List.get(0).getUser_id().equals(Id_user)) {
//                    editbnt.setVisibility(View.VISIBLE);
//                } else {
//                    editbnt.setVisibility(View.INVISIBLE);
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

    public void btn_edit(View view) {
        PopupMenu popupMenu = new PopupMenu(Fiche_Produite.this, editbnt);
        popupMenu.getMenuInflater().inflate(R.menu.menu_ficheproduit, popupMenu.getMenu());
        popupMenu.show();
    }

    public void modifer(MenuItem item) {
        startActivity(new Intent(this, Modifier_Annonce.class));
    }

    public void supprimer(MenuItem item) {

    }
}

