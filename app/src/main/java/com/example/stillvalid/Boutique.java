package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;

public class Boutique extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    List<Post> postList = new ArrayList<>();
    RelativeLayout relativeLayout;
    public static final String Boutique_URL = "http://192.168.1.21/StillValid/Boutique.php";
    ImageView btn_menu;
    int id_annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        recyclerView = findViewById(R.id.recycler_boutique);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadboutique();
        btn_menu = findViewById(R.id.menu);

        relativeLayout = findViewById(R.id.Rel_boutique);
        prefs = getSharedPreferences("Boutique", MODE_PRIVATE);
        editors = prefs.edit();


    }

    private void loadboutique() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Boutique_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray produit = new JSONArray(response);

                            for (int i = 0; i < produit.length(); i++) {
                                JSONObject produitobject = produit.getJSONObject(i);
                                int id_annonce = produitobject.getInt("id");
                                String nom_produit = produitobject.getString("titre");
                                String lieu = produitobject.getString("ville");
                                String image = produitobject.getString("photoProduit");
                                String prix = produitobject.getString("prix");


                                Post product = new Post(id_annonce, nom_produit, lieu, image, prix);
                                postList.add(product);
                            }
                            postsAdapter = new PostsAdapter(Boutique.this, postList);
                            recyclerView.setAdapter(postsAdapter);

                            recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView,
                                    new RecyclerViewClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {
                                            TextView txt = view.findViewById(R.id.id);
                                            editors.putString("Id_Annonce", txt.getText().toString());
                                            editors.commit();
//                                            Toast.makeText(Boutique.this, txt.getText(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Fiche_Produite.class);
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
                Toast.makeText(Boutique.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }
    /*public void btn_fiche_prod (View view){

        startActivity(new Intent(this,Fiche_Produite.class));}*/

    public void getMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(Boutique.this, btn_menu);
        popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
        popupMenu.show();
    }
}