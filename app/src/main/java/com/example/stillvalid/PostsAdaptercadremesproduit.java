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

public class PostsAdaptercadremesproduit extends RecyclerView.Adapter<PostsAdaptercadremesproduit.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_con, duree_con;
        CircleImageView image_con;

        public ViewHolder(View itemView) {
            super(itemView);
            name_con = itemView.findViewById(R.id.txt_nom_contrat);
            duree_con = itemView.findViewById(R.id.txt_duree_contrat);
            image_con = itemView.findViewById(R.id.img_contart);

        }
    }

    private Context context;
    private List<Postcadremesproduit> posts;

    public PostsAdaptercadremesproduit(Context c, List<Postcadremesproduit> postList) {
        this.context = c;
        posts = postList;
    }

    @NonNull
    @Override
    public PostsAdaptercadremesproduit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_mesproduit2, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Postcadremesproduit p = posts.get(position);
        holder.name_con.setText(p.getTxt_nom_contrat());
        holder.duree_con.setText(p.getTxt_duree_contrat());
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


}

