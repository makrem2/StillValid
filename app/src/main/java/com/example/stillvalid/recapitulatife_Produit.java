package com.example.stillvalid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class recapitulatife_Produit extends AppCompatActivity {
    EditText enseigneachatt, produitt, dateeachat, dureeegarantie;
    TextView btn_menu;
    ProgressDialog progressDialog;
    DatePickerDialog picker;
    SharedPreferences.Editor editors;
    Calendar myCalendar = Calendar.getInstance();
    ImageView img_prod, img_facture, echec_prod, echec_fact;
    SharedPreferences prefs, prefs1, prefss;
    String Enseigne, Marqueshared, Marque, NomProduit, Dateeachat, Dureegrantie, userid, Facture, Article, Sav, Date_Fin;
    String insertProduit = "http://192.168.1.18/StillValid/AjouterProduit.php";
    Spinner marquee;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Marque = new ArrayList<>();
    List<marque> listMarques = new ArrayList<>();
    ArrayList<String> sav = new ArrayList<>();
    public static final String ENSEIGNE = "enseigne";
    public static final String NOM = "nom";
    public static final String DACHAT = "dAchat";
    public static final String GARANTIE = "garantie";
    public static final String MARQUE = "marque";
    public static final String SAV = "sav";
    public static final String FACTURE = "facture";
    public static final String ARTICLE = "photo";
    public static final String DFIN = "dFin";
    public static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatife__produit);

        enseigneachatt = findViewById(R.id.edit_enseigne_achat);
        produitt = findViewById(R.id.edit_produit);
        dateeachat = findViewById(R.id.edit_date_achat);
        dureeegarantie = findViewById(R.id.edit_duree_garentie);
        marquee = findViewById(R.id.edit_marque);
        img_prod = findViewById(R.id.img_prod);
        img_facture = findViewById(R.id.img_facture);
        echec_prod = findViewById(R.id.echec_prod);
        echec_fact = findViewById(R.id.echec_fact);
        btn_menu = findViewById(R.id.txt_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(recapitulatife_Produit.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });


        prefs1 = getSharedPreferences("login", MODE_PRIVATE);
        userid = prefs1.getString("id", null);

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();
        Enseigne = prefs.getString("Enseigne", null);
        enseigneachatt.setText(Enseigne);
        NomProduit = prefs.getString("Nom_Produit", null);
        produitt.setText(NomProduit);

        Marqueshared = prefs.getString("Marque", null);


        Dateeachat = prefs.getString("dateachat", null);
        dateeachat.setText(Dateeachat);
        Dureegrantie = prefs.getString("duree garentie", null);
        dureeegarantie.setText(Dureegrantie);
        Article = prefs.getString("Photo_Article", null);
        Facture = prefs.getString("Photo_Facture", null);


        prefss = getSharedPreferences("sav", MODE_PRIVATE);
        Sav = prefss.getString("marquesav", null);

        if ((Article != null)) {
            img_prod.setImageResource(R.drawable.ic_checked);

        } else {
            echec_prod.setImageResource(R.drawable.ic_x);
        }
        if ((Facture != null)) {
            img_facture.setImageResource(R.drawable.ic_checked);

        } else {
            echec_fact.setImageResource(R.drawable.ic_x);
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.18/StillValid/GetALLMarque.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                        listMarques.add(new marque(response.getJSONObject(i).getString("id"),
                                response.getJSONObject(i).getString("libelle"),
                                response.getJSONObject(i).getString("sav"),
                                response.getJSONObject(i).getString("support")));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                    marquee.setAdapter(Adapter);
                    marquee.setSelection(TrouverIndice(Marqueshared));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recapitulatife_Produit.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        marquee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listMarques.get(i).getSav();
                Marque = Adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void return_ajou_ph_prod(View view) {
        startActivity(new Intent(this, Ajouter_Photo_Produit.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void valid_recap(View view) {

        Enseigne = enseigneachatt.getText().toString().trim();
        NomProduit = produitt.getText().toString().trim();
        Dateeachat = dateeachat.getText().toString().trim();
        Dureegrantie = dureeegarantie.getText().toString().trim();
        Calendar cal = Calendar.getInstance();
        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
            try {
                cal.setTime(sdf.parse(Dateeachat));
                cal.add(Calendar.MONTH, Integer.parseInt(Dureegrantie));
                Date_Fin = sdf.format(cal.getTime());
                editors.putString("duree garentie", Dureegrantie);
                editors.putString("Date_Fin", sdf.format(cal.getTime()));
                editors.apply();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Enseigne.isEmpty() || (NomProduit.isEmpty()) || (Dateeachat.isEmpty()) || (Dureegrantie.isEmpty())) {

                Toast.makeText(this, "champ(s) doit être(s) remplit(s) ", Toast.LENGTH_SHORT).show();
            } else {

                RequestQueue requestQueue = Volley.newRequestQueue(recapitulatife_Produit.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, insertProduit, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.isEmpty()) {
                            Log.i("Myresponse", "Successfully ADD" + response);
                            Toast.makeText(recapitulatife_Produit.this, "" + response, Toast.LENGTH_SHORT).show();
                            Ajouter_Photo_Produit.PHOTOFACTURE = null;
                            Ajouter_Photo_Produit.PHOTOARTICLE = null;
                        } else {

                            Toast.makeText(recapitulatife_Produit.this, "errr", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Mysmart", "" + error);
                        Toast.makeText(recapitulatife_Produit.this, "" + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put(USER_ID, userid);
                        param.put(ENSEIGNE, Enseigne);
                        param.put(NOM, NomProduit);
                        param.put(DACHAT, Dateeachat);
                        param.put(GARANTIE, Dureegrantie);
                        param.put(MARQUE, Marque);
                        param.put(SAV, Sav);
                        param.put(DFIN, Date_Fin);
                        param.put(FACTURE, getStringImage(Ajouter_Photo_Produit.PHOTOFACTURE));
                        param.put(ARTICLE, getStringImage(Ajouter_Photo_Produit.PHOTOARTICLE));


                        return param;
                    }
                };

                requestQueue.add(stringRequest);
            }
        }


    public String getStringImage(Bitmap bitmap) {
        Log.i("MyHitesh", "" + bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
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

    public void getDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();

            }
        }, year, month, day);
        picker.show();
    }

    private void updateLabel() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        dateeachat.setText(sdf.format(myCalendar.getTime()));
    }
    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(recapitulatife_Produit.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}

