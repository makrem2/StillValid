package com.example.stillvalid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Date_achat extends AppCompatActivity {
    EditText Date;
    DatePickerDialog picker;
    Calendar myCalendar=Calendar.getInstance();
    ImageView btn_menu;
    SharedPreferences prefs;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_achat);

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();


        Date = findViewById(R.id.sp_date_achat);

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Date_achat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
    }

    public void valid_date_achat(View view) {
        String date_achat = Date.getText().toString();
        if (!date_achat.isEmpty()) {
            Toast.makeText(this, date_achat, Toast.LENGTH_SHORT).show();
            editors.putString("dateachat", date_achat);
            editors.apply();
            startActivity(new Intent(this, Duree_garantie.class));
        } else {
            Date.setError("Champ obligatoire");
        }

    }

    public void return_ajou_nom_prod(View view) {
        startActivity(new Intent(this, Nom_Produit.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }


    public void vocale(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Sorry! Your device doesn't support speech language!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> listResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Date.setText(listResult.get(0));
                }
                break;
        }
    }

    public void btn_efface(View view) {
            Date.setText("");
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
        Date.setText(sdf.format(myCalendar.getTime()));
    }
    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(Date_achat.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}
