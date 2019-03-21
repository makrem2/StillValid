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

public class Date_echence extends AppCompatActivity {
    EditText Date;
    private static final String TAG = "Date_echence";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ImageView btn_menu;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_echence);


        prefs = getSharedPreferences("ajoutercontart", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.img_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Date_echence.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        Date = findViewById(R.id.sp_date_echeance);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Date_echence.this,
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
                Date.setText(date);
            }
        };
    }

    public void valid_echeance(View view) {
        String date = Date.getText().toString();
        if (!date.isEmpty()) {
            editors.putString("dateecheance", date);
            editors.apply();
            startActivity(new Intent(this, Ajouter_photo_contrat.class));
        } else {
            Date.setError("Champ obligatoire");
        }


    }

    public void return_type_contrat(View view) {
        startActivity(new Intent(this, Type_contrat.class));
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
