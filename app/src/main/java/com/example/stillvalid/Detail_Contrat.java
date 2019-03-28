package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detail_Contrat extends AppCompatActivity {
    ImageView btn_menu;
    List<Post> postList = new ArrayList<>();
    TextView Fiche_Contrat, dEcheance, TypeContart;
    CircleImageView imagecontrat;
    String id_contrat;
    ProgressDialog progressDialog;
    int jours;
    SharedPreferences prefscontart;

    public String url = "http://192.168.1.18/StillValid/ContratById.php?id_contrat=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__contrat);

        Fiche_Contrat = findViewById(R.id.txt_fiche_contrat);
        dEcheance = findViewById(R.id.txt_Dat);
        TypeContart = findViewById(R.id.typecontart);
        imagecontrat = findViewById(R.id.profile_image);

        prefscontart = getSharedPreferences("mescontart", MODE_PRIVATE);

        String restoredid = prefscontart.getString("Id_Contrat", null);
        String restoredjour = prefscontart.getString("Nb_joursconrat", null);

        if (restoredjour != null) {
            jours = Integer.parseInt(restoredjour);
            if (jours <= 0) {
                imagecontrat.setBorderColor(Color.RED);

            } else {
                imagecontrat.setBorderColor(Color.parseColor("#358c42"));

            }
           // Toast.makeText(this, jours + "", Toast.LENGTH_SHORT).show();
        }

        if (restoredid != null) {
            id_contrat = restoredid;
            loadcontrat();
            //Toast.makeText(this, id_contrat, Toast.LENGTH_SHORT).show();
        }

        btn_menu = findViewById(R.id.img_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Detail_Contrat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    public void loadcontrat() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_contrat, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    dEcheance.setText(response.getJSONObject(0).getString("dEcheance"));
                    TypeContart.setText(response.getJSONObject(0).getString("type"));

                    Picasso.get()
                            .load(response.getJSONObject(0).getString("photo"))
                            .resize(400, 500)
                            .into(imagecontrat);

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


    public void bn_return_Mes_Produit(View view) {
        startActivity(new Intent(this, MesProduits.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void EditContrat(View view) {
        startActivity(new Intent(this, modifier_contrat.class));
    }

    public void VoirContrat(View view) {

        startActivity(new Intent(this, Voir_Contrat.class));

    }

    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }

    public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }

    public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }

    public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(Detail_Contrat.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}
