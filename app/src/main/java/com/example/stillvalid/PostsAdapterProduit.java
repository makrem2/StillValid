package com.example.stillvalid;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapterProduit extends RecyclerView.Adapter<PostsAdapterProduit.ViewHolder> {

    int jour = 0;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_prod, duree, id_produit,jours;
        CircleImageView image;
        public ViewHolder(View itemView) {
            super(itemView);

            name_prod = itemView.findViewById(R.id.txtDat);
            duree = itemView.findViewById(R.id.txtgarantie);
            image = itemView.findViewById(R.id.profile_image);
            id_produit = itemView.findViewById(R.id.id_prod);
            jours = itemView.findViewById(R.id.jours);

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

        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar_Inst = Calendar.getInstance();
        Calendar calendar_final = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar_Inst.add(Calendar.DAY_OF_MONTH, -1);
        try {
            String dateFin = p.getTxt_duree();
            calendar.setTime(sdf.parse(dateFin));
            calendar_final.setTimeInMillis(calendar.getTimeInMillis() - calendar_Inst.getTimeInMillis());
            jour = (int) (calendar_final.getTimeInMillis() / 86400000);
            holder.duree.setText(String.valueOf(jour));
            if (jour<=0){
                holder.duree.setTextColor(Color.RED);
                holder.image.setBorderColor(Color.RED);
                holder.jours.setTextColor(Color.RED);
            }else {
                holder.duree.setTextColor(Color.parseColor("#358c42"));
                holder.image.setBorderColor(Color.parseColor("#358c42"));
                holder.jours.setTextColor(Color.parseColor("#358c42"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.name_prod.setText(p.getTxt_prod());
        holder.id_produit.setText(String.valueOf(p.getId_prod()));
        Picasso.get()
                .load(p.getImg_prod())
                .resize(400, 500)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
