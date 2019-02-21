package com.example.stillvalid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom_produit, lieu,prix;
        CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            nom_produit = itemView.findViewById(R.id.txt_nom_produit);
            lieu = itemView.findViewById(R.id.txt_lieu_produit);
            prix = itemView.findViewById(R.id.txt_prix_produit);
            image = itemView.findViewById(R.id.img_prod);

        }
    }

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context c, List<Post> postList) {
        this.context = c;
        posts = postList;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_boutique, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        Post p = posts.get(position);
        holder.nom_produit.setText(p.getTxt_nom_produit());
        holder.lieu.setText(p.getTxt_lieu_produit());
        holder.prix.setText(p.getTxt_prix_produit());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
