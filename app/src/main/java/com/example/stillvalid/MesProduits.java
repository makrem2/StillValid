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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesProduits extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView_contrat;
    private PostsAdapterProduit postsAdapterProduit;
    private PostsAdaptercadremesproduit postsAdaptercadremesproduit;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostProduit> posts ;
    private ArrayList<Postcadremesproduit> posts_con ;
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_produits);
        recyclerView = findViewById(R.id.RV_post);
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


        posts_con = new ArrayList<>();
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
        recyclerView.setAdapter(postsAdapterProduit);
}
    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

        public void btn_Detail_contrat (View view){
        startActivity(new Intent(this,Detail_Contrat.class));}

    public void btn_Detail_prod (View view){
        startActivity(new Intent(this,DetaileProduits.class));}
}


