package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MesProduits extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView_contrat;
    private PostsAdapterProduit postsAdapterProduit;
    private PostsAdaptercadremesproduit postsAdaptercadremesproduit;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostProduit> posts ;
    private ArrayList<Postcadremesproduit> posts_con ;
    public static final String Produit_URL = "http://192.168.1.7/StillValid/mesproduit.php";
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_produits);

        recyclerView = findViewById(R.id.RV_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager (new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



        recyclerView_contrat = findViewById(R.id.Rv_contrat_abon);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MesProduits.this,btn_menu);
            popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
            popupMenu.show();
            }
        });

        /*  posts_con = new ArrayList<>();
        for (int i = 0 ; i<3;i++){
            posts_con.add(new Postcadremesproduit("txt_nom_contrat"+i , "txt_duree_contrat"+i,""));
        }
        posts = new ArrayList<>();
        for (int i = 0 ; i<3;i++){
        posts.add(new PostProduit("txt_prod"+i , "txt_duree"+i,""));
    }
        postsAdapterProduit = new PostsAdapterProduit(this,posts);
        postsAdaptercadremesproduit = new PostsAdaptercadremesproduit(this,posts_con);

        recyclerView_contrat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView_contrat.setAdapter(postsAdaptercadremesproduit);

        recyclerView.setLayoutManager (new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(postsAdapterProduit);*/

        loadProduit();




    }


    private  void loadProduit(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Produit_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray produit = new JSONArray(response);

                            for (int i = 0; i <produit.length();i++){

                                JSONObject produitobhect = produit.getJSONObject(i);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MesProduits.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }






    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

        public void btn_Detail_contrat (View view){
        startActivity(new Intent(this,Detail_Contrat.class));}

    public void btn_Detail_prod (View view){
        startActivity(new Intent(this,DetaileProduits.class));}
}


