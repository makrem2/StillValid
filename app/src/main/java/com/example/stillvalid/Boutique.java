package com.example.stillvalid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

public class Boutique extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Post> posts;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        recyclerView = findViewById(R.id.recycler_boutique);

        posts = new ArrayList<>();
        for (int i = 0 ; i<10;i++){
           // posts.add(new Post("txt_prod"+i , "txt_duree"+i,""));
            posts.add(new Post("txt_nom_produit"+i,"txt_lieu_produit"+i,"txt_prix_produit"+i,""));
        }
        postsAdapter = new PostsAdapter(this,posts);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postsAdapter);
    }
}