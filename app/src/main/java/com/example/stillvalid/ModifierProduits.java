package com.example.stillvalid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModifierProduits extends AppCompatActivity {
    ImageView btn_menu, profile_image;
    SharedPreferences prefs;
    private static final String TAG = "Date_echence";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText ensegineproduit, dureegrantie, nomproduit, date_achat;
    TextView modifierphoto, modifierfacture;
    Spinner marquee;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Marque = new ArrayList<>();
    List<marque> listMarques = new ArrayList<>();
    public static final String ID_PRODUIT = "id_produit";
    public static final String ENSEGINE = "enseigne";
    public static final String NOM_PRODUIT = "nom";
    public static final String GARANTIE = "garantie";
    public static final String DATE_ACHAT = "dAchat";

    String Ensegine, Nom, Garantie, DAchat, id_produit, Marque;

    public static final String DATA_URL = "http://192.168.1.18/StillValid/GetALLMarque.php";
    public static final String Modif_URL = "http://192.168.1.18/StillValid/Modifier_ProduitById.php";
    public String url = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_produits);



        ensegineproduit = findViewById(R.id.edit_ensegine_achat);
        dureegrantie = findViewById(R.id.edit_garantie);
        nomproduit = findViewById(R.id.edit_produit);
        date_achat = findViewById(R.id.edit_date_achat);
        modifierphoto = findViewById(R.id.modifierphoto);
        modifierfacture = findViewById(R.id.modifierfacture);
        marquee = findViewById(R.id.sp_marque);
        profile_image = findViewById(R.id.profile_image);

        date_achat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ModifierProduits.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/"+year);
                String date = month + "/" + day + "/" + year;
                date_achat.setText(date);
            }
        };

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ModifierProduits.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });


        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.18/StillValid/GetALLMarque.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                        //sav.add(response.getJSONObject(i).getString("sav"));
                        listMarques.add(new marque(response.getJSONObject(i).getString("id"),
                                response.getJSONObject(i).getString("libelle"),
                                response.getJSONObject(i).getString("sav"),
                                response.getJSONObject(i).getString("support")));

                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                    marquee.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifierProduits.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(request2);
        marquee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter.getItem(i);
                listMarques.get(i).getSav();


                Toast.makeText(ModifierProduits.this, Adapter.getItem(i) + "", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);
        String restoredid = prefs.getString("Id_Produit", null);
        if (restoredid != null) {
            id_produit = restoredid;
            Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_produit, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        ensegineproduit.setText(response.getJSONObject(0).getString("enseigne"));
                        dureegrantie.setText(response.getJSONObject(0).getString("garantie"));
                        marquee.setSelection(TrouverIndice(response.getJSONObject(0).getString("marque")));
                        nomproduit.setText(response.getJSONObject(0).getString("nom"));
                        date_achat.setText(response.getJSONObject(0).getString("dAchat"));
                        //Toast.makeText(ModifierProduits.this, date_achat+"", Toast.LENGTH_SHORT).show();

                        Picasso.get()
                                .load(response.getJSONObject(0).getString("photo"))
                                .resize(400, 500)
                                .into(profile_image);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void modifphoto(View view) {
    }

    public void modiffacture(View view) {
    }

    public int TrouverIndice(String Marque) {
        int i = 0;
        for (String item : List_Marque) {
            if (item.equals(Marque)) {
                return i;
            }
            i++;
        }
        return 0;
    }

}
