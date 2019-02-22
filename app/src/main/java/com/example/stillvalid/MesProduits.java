package com.example.stillvalid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesProduits extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostsAdapterProduit postsAdapterProduit;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PostProduit> posts;
    CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_produits);
        recyclerView = findViewById(R.id.RV_post);

    posts = new ArrayList<>();
        for (int i = 0 ; i<3;i++){
        posts.add(new PostProduit("txt_prod"+i , "txt_duree"+i,""));
    }
        postsAdapterProduit = new PostsAdapterProduit(this,posts);
    //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager (new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(postsAdapterProduit);
}
}


