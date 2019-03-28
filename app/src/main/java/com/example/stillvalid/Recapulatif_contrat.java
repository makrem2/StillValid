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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Recapulatif_contrat extends AppCompatActivity {
    ImageView btn_menu, checkimg, echec;
    ProgressDialog progressDialog;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    String typecontrat, dateecheance, importphoto, userid;
    ArrayAdapter<String> Adapter;
    DatePickerDialog picker;
    Calendar myCalendar=Calendar.getInstance();
    String insertcontrat = "http://192.168.1.18/StillValid/AjouterContrat.php";
    ArrayList<String> List_Marque = new ArrayList<>();
    Bitmap bitmap;
    EditText Dateechance;
    Spinner Typecontrat;

    public static final String TYPECONTRAT = "type";
    public static final String DATEECHANCE = "dEcheance";
    public static final String IMPORT_PHOTO = "photo";
   // public static final String PRENDRE_PHOTO = "prendre_photo";
    public static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapulatif_contrat);

        Typecontrat = findViewById(R.id.edit_type);
        Dateechance = findViewById(R.id.edit_date_echance);
        checkimg = findViewById(R.id.checkimg);
        echec = findViewById(R.id.echec);
        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Recapulatif_contrat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        prefs = getSharedPreferences("login", MODE_PRIVATE);
        userid = prefs.getString("id", null);

        prefs = getSharedPreferences("ajoutercontart", MODE_PRIVATE);
        typecontrat = prefs.getString("typecontrat", null);
        dateecheance = prefs.getString("dateecheance", null);
        importphoto = prefs.getString("Photo_Contrat", null);


        Dateechance.setText(dateecheance);
        Toast.makeText(this, Ajouter_photo_contrat.PHOTO + "", Toast.LENGTH_SHORT).show();

        if (importphoto != null) {
            checkimg.setImageResource(R.drawable.ic_checked);

        } else {
            echec.setImageResource(R.drawable.ic_x);
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.18/StillValid/GetALLTypes.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                    Typecontrat.setAdapter(Adapter);
                    Typecontrat.setSelection(TrouverIndice(typecontrat));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Recapulatif_contrat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    public void return_ajou_ph_contrat(View view) {
        startActivity(new Intent(this, Ajouter_photo_contrat.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void recapulatifcontart(View view) {
        typecontrat = Typecontrat.getSelectedItem().toString();
        dateecheance = Dateechance.getText().toString().trim();

        if (dateecheance.isEmpty()){

            Toast.makeText(this, "champ(s) doit être(s) remplit(s) ", Toast.LENGTH_SHORT).show();
        }else {


        RequestQueue requestQueue = Volley.newRequestQueue(Recapulatif_contrat.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertcontrat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Log.i("Myresponse", "" + response);
                    Toast.makeText(Recapulatif_contrat.this, "" + response, Toast.LENGTH_SHORT).show();
                    Ajouter_photo_contrat.PHOTO = null;
                } else {

                    Toast.makeText(Recapulatif_contrat.this, "errr", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Mysmart", "" + error);
                Toast.makeText(Recapulatif_contrat.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put(USER_ID, userid);
                param.put(TYPECONTRAT, typecontrat);
                param.put(DATEECHANCE, dateecheance);
                param.put(IMPORT_PHOTO, getStringImage(Ajouter_photo_contrat.PHOTO));


                return param;
            }
        };

        requestQueue.add(stringRequest);
    }}

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
        picker = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
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
        Dateechance.setText(sdf.format(myCalendar.getTime()));
    }
    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(Recapulatif_contrat.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}

