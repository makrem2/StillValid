package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Boutique extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Post> postList=new ArrayList<>();
    public static final String Boutique_URL = "http://192.168.1.21/StillValid/Boutique.php";
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        recyclerView = findViewById(R.id.recycler_boutique);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadboutique();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Boutique.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });
        /*posts = new ArrayList<>();
        for (int i = 0 ; i<10;i++){
           // posts.add(new Post("txt_prod"+i , "txt_duree"+i,""));
            posts.add(new Post("txt_nom_produit"+i,"txt_lieu_produit"+i,"txt_prix_produit"+i,""));
        }
        postsAdapter = new PostsAdapter(this,posts);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postsAdapter);*/
    }

    private  void loadboutique(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Boutique_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray produit = new JSONArray(response);

                            for (int i = 0; i <produit.length();i++){

                                JSONObject produitobject = produit.getJSONObject(i);
                                String nom_produit = produitobject.getString("titre");
                                String lieu = produitobject.getString("ville");
                                String prix =  produitobject.getString("photoProduit");
                                String image = produitobject.getString("prix");

                                Post product = new Post(nom_produit,lieu,prix,image);
                                postList.add(product);

                            }
                            postsAdapter = new PostsAdapter(Boutique.this,postList);
                            recyclerView.setAdapter(postsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Boutique.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}
    public void btn_fiche_prod (View view){
        startActivity(new Intent(this,Fiche_Produite.class));}
}