package com.example.stillvalid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapterProduit extends RecyclerView.Adapter<PostsAdapterProduit.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_prod, duree , id_produit;
        CircleImageView image;
        public ViewHolder(View itemView) {
            super(itemView);

            name_prod = itemView.findViewById(R.id.txtDat);
            duree = itemView.findViewById(R.id.txtgarantie);
            image = itemView.findViewById(R.id.profile_image);
            id_produit = itemView.findViewById(R.id.id_prod);

        }
    }
    private Context context;
    private List<PostProduit> posts;

    public PostsAdapterProduit(Context c, List<PostProduit> postList) {
        this.context = c;
        posts = postList;
    }

    @NonNull
    @Override
    public PostsAdapterProduit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_mes_produits, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapterProduit.ViewHolder holder, int position) {
        PostProduit p = posts.get(position);
        holder.name_prod.setText(p.getTxt_prod());
        holder.id_produit.setText(String.valueOf(p.getId_prod()));
        holder.duree.setText(p.getTxt_duree());
        Glide.with(context).load(p.getImg_prod()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
