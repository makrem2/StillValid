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
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdaptercadremesproduit extends RecyclerView.Adapter<PostsAdaptercadremesproduit.ViewHolder> {
    int jour = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_con, duree_con,ID_USER,ID_CONTRAT,jours;
        RoundedImageView image_con;

        public ViewHolder(View itemView) {
            super(itemView);
            ID_USER = itemView.findViewById(R.id.iduser_contrat);
            ID_CONTRAT=itemView.findViewById(R.id.id_contrat);
            name_con = itemView.findViewById(R.id.txt_nom_contrat);
            duree_con = itemView.findViewById(R.id.txt_duree_contrat);
            image_con = itemView.findViewById(R.id.img_contart);
            jours = itemView.findViewById(R.id.jours);
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

        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar_Inst = Calendar.getInstance();
        Calendar calendar_final = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar_Inst.add(Calendar.DAY_OF_MONTH, -1);
        try {
            String dateFin = p.getTxt_duree_contrat();
            calendar.setTime(sdf.parse(dateFin));
            calendar_final.setTimeInMillis(calendar.getTimeInMillis() - calendar_Inst.getTimeInMillis());
            jour = (int) (calendar_final.getTimeInMillis() / 86400000);
            holder.duree_con.setText(String.valueOf(jour));
            if (jour<=0){
                holder.duree_con.setTextColor(Color.RED);
                holder.image_con.setBorderColor(Color.RED);
                holder.jours.setTextColor(Color.RED);
            }else {
                holder.duree_con.setTextColor(Color.parseColor("#358c42"));
                holder.jours.setTextColor(Color.parseColor("#358c42"));
                holder.image_con.setBorderColor(Color.parseColor("#358c42"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.ID_USER.setText(String.valueOf(p.getIdUser()));
        holder.ID_CONTRAT.setText(String.valueOf(p.getId()));
        holder.name_con.setText(p.getTxt_nom_contrat());
       //holder.duree_con.setText(p.getTxt_duree_contrat());


        Picasso.get()
                .load(p.getImg_contrat())
                .resize(400, 500)
                .into(holder.image_con);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


}

