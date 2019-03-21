package com.example.stillvalid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom_produit, lieu,prix,id,ID_USER;
        ImageView image;
        ImageView btnfiche;

        public ViewHolder (View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            ID_USER = itemView.findViewById(R.id.id_user_b);
            nom_produit = itemView.findViewById(R.id.txt_nom_produit);
            lieu = itemView.findViewById(R.id.txt_lieu_produit);
            prix = itemView.findViewById(R.id.txt_prix_produit);
            image = itemView.findViewById(R.id.img_nom_produit);

        }
    }
    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false);
        return new PostsAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        holder.ID_USER.setText(String.valueOf(posts.get(position).getIdUser()));
        holder.nom_produit.setText(posts.get(position).getNom_produit());
        holder.lieu.setText(posts.get(position).getLieu());
        holder.id.setText(String.valueOf(posts.get(position).getId()));
        Glide.with(context)
                .load(posts.get(position).getImg_nom_produit())
                .into(holder.image);
        holder.prix.setText(posts.get(position).getPrix());
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }
}
