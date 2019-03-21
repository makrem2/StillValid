package com.example.stillvalid;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Date_achat extends AppCompatActivity {
    EditText Date;
    DatePickerDialog datePickerDialog;
    private static final String TAG = "Date_achat";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ImageView btn_menu;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_achat);

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();


        Date = findViewById(R.id.sp_date_achat);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Date_achat.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date =  day+ "/" + month + "/" + year;
                Date.setText(date);
            }
        };

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
        String Text = Date.getText().toString();
        if (Text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            Date.setText("");
        }


    }
}
