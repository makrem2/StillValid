package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stillvalid.Helper.RecyclerViewClickListener;
import com.example.stillvalid.Helper.RecyclerViewTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Thread.sleep;

public class MesProduits extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerView_contrat;
    private PostsAdapterProduit postsAdapterProduit;
    ProgressDialog progressDialog;
    List<PostProduit> postList = new ArrayList<>();
    private PostsAdaptercadremesproduit postsAdaptercadremesproduit;
    List<Postcadremesproduit> postcadremesproduits = new ArrayList<>();
    String id_user, nbjours_produit, nbjours_contrat;
    SharedPreferences prefs, prefss, prefsphotoprod, prefscontart;
    SharedPreferences.Editor editors, editorsphotoprod, editorscontart;
    public static final String Produit_URL = "http://192.168.1.18/StillValid/mesproduit.php?id_user=";
    public static final String Contrat_URL = "http://192.168.1.18/StillValid/mescontrat.php?id_user=";
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_produits);
        recyclerView = findViewById(R.id.RV_post);
        recyclerView.setHasFixedSize(true);
        recyclerView_contrat = findViewById(R.id.Rv_contrat_abon);
        recyclerView_contrat.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_contrat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);
        editors = prefs.edit();
        prefss = getSharedPreferences("login", MODE_PRIVATE);
        id_user = prefss.getString("id", null);
        prefscontart = getSharedPreferences("mescontart", MODE_PRIVATE);
        editorscontart = prefscontart.edit();

        prefsphotoprod = getSharedPreferences("photo_produit", MODE_PRIVATE);
        editorsphotoprod = prefsphotoprod.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MesProduits.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        loadProduit();
        loadContart();
    }

    private void loadProduit() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Produit_URL + id_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray produits = new JSONArray(response);
                            for (int i = 0; i < produits.length(); i++) {
                                JSONObject produitsObject = produits.getJSONObject(i);
                                int id_produit = produitsObject.getInt("id");
                                String name_prod = produitsObject.getString("nom");
                                String getdatefin = produitsObject.getString("dFin");
                                String image = produitsObject.getString("photo");

                                editorsphotoprod.putString("photoprod", image);
                                editorsphotoprod.apply();

                                PostProduit produit = new PostProduit(id_produit, name_prod, getdatefin, image, "");
                                postList.add(produit);
                            }
                            postsAdapterProduit = new PostsAdapterProduit(MesProduits.this, postList);
                            recyclerView.setAdapter(postsAdapterProduit);
                            recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView,
                                    new RecyclerViewClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {
                                            TextView text = view.findViewById(R.id.id_prod);
                                            TextView jour = view.findViewById(R.id.txtgarantie);
                                            editors.putString("Id_Produit", text.getText().toString());
                                            editors.putString("Nb_jours", jour.getText().toString());
                                            editors.commit();
//                                            Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), DetaileProduits.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onLongClick(View view, int position) {
                                        }
                                    }));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MesProduits.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void loadContart() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Contrat_URL + id_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray contart = new JSONArray(response);
                    for (int i = 0; i < contart.length(); i++) {
                        JSONObject contartObject = contart.getJSONObject(i);
                        int ID_CONTRAT = contartObject.getInt("id");
                        int ID_USER = contartObject.getInt("user_id");
                        String name_con = contartObject.getString("type");
                        String getdatefin = contartObject.getString("dEcheance");
                        String image_con = contartObject.getString("photo");

                        Postcadremesproduit Contart = new Postcadremesproduit(ID_CONTRAT, ID_USER, name_con, getdatefin, image_con, "");
                        postcadremesproduits.add(Contart);
                    }
                    postsAdaptercadremesproduit = new PostsAdaptercadremesproduit(MesProduits.this, postcadremesproduits);
                    recyclerView_contrat.setAdapter(postsAdaptercadremesproduit);
                    recyclerView_contrat.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView_contrat,
                            new RecyclerViewClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    TextView txt = view.findViewById(R.id.id_contrat);
                                    TextView jourcontrat = view.findViewById(R.id.txt_duree_contrat);
                                    editorscontart.putString("Id_Contrat", txt.getText().toString());
                                    editorscontart.commit();

                                    editorscontart.putString("Nb_joursconrat", jourcontrat.getText().toString());
                                    editorscontart.commit();

                                    Intent intent = new Intent(getApplicationContext(), Detail_Contrat.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {
                                }
                            }));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MesProduits.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
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
        progressDialog = new ProgressDialog(MesProduits.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}


