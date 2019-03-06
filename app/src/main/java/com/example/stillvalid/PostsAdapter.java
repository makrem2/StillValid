package com.example.stillvalid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom_produit, lieu,prix;
        ImageView image;

        public ViewHolder (View itemView) {
            super(itemView);
            nom_produit = itemView.findViewById(R.id.txt_nom_produit);
            lieu = itemView.findViewById(R.id.txt_lieu_produit);
            prix = itemView.findViewById(R.id.txt_prix_produit);
            image = itemView.findViewById(R.id.img_nom_produit);

        }
    }



    public PostsAdapter(Context c, List<Post> postList) {
        this.context = c;
        this.posts = postList;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        Post p = posts.get(position);
        holder.nom_produit.setText(p.getNom_produit());
        holder.lieu.setText(p.getLieu());

        Glide.with(context)
                .load(p.getImg_nom_produit())
                .into(holder.image);
        holder.prix.setText(p.getPrix());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
