package com.example.stillvalid;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Duree_garantie extends AppCompatActivity {

    ImageView btn_menu;
    EditText duree_garentie, dddd;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    private TextInputLayout textInputgrantie;
    String Dateeachat;
    String Duree;
    String Dfin;
    Calendar cal;

    private static final Pattern Duree_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +        //at least 1 digit
                    //"(?=.*[a-z])" +        //at least 1 lower case letter
                    //"(?=.*[A-Z])" +        //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +       //any letter
                    //"(?=.*[@#$%^&+=])" +   //at least 1 special character
                    //"(?=\\S+$)" +            //no white spaces
                    ".{1,}" +                //at least 1 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duree_garantie);

        duree_garentie = findViewById(R.id.sp_duree_garentie);
        textInputgrantie = findViewById(R.id.text_input_garentie);
        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();
        dddd = findViewById(R.id.dddd);


        Dateeachat = prefs.getString("dateachat", null);
        Duree = Dateeachat;


        btn_menu = findViewById(R.id.img_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Duree_garantie.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
//qfsqfsqqsfqfqsfqsfqfqfqfqfqfqfqfsqf

        Calendar cal = Calendar.getInstance();
        String oldDate = "2017-01-29";
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
//        String CurrentDate = mYear + "/" + mMonth + "/" + mDay;
        //DatePickerDialog datePickerDialog;
        //String dateInString = Duree; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            cal.setTime(sdf.parse(oldDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cal.add(Calendar.MONTH, 1);//insert the number of month

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(cal.getTimeInMillis());
        Duree = sdf.format(resultdate);

        //Display the Result in the Edit Text or Text View your Choice

        dddd.setText(Duree);

        //qfsqsfsqfsqfqsfqsfqsfsqfsqfsqfsqfsq
    }


    private boolean validateDuree() {
        String grantieInput = textInputgrantie.getEditText().getText().toString().trim();
        if (grantieInput.isEmpty()) {
            textInputgrantie.setError("Field can't be empty");
            return false;
        } else if (!Duree_PATTERN.matcher(grantieInput).matches()) {
            textInputgrantie.setError("must be number");
            return false;
        } else {
            textInputgrantie.setError(null);
            return true;
        }
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
                    duree_garentie.setText(listResult.get(0));
                }
                break;
        }
    }

    public void valid_duree_garantie(View view) {

        String dureegrantie = duree_garentie.getText().toString();
        if (!validateDuree()) {
            return;
        } else if (!dureegrantie.isEmpty()) {
            editors.putString("duree garentie", dureegrantie);

            Toast.makeText(this, "" + duree_garentie.getText(), Toast.LENGTH_SHORT).show();
            //editors.putString("date_fin",(addMonth(Dfin)));
            //editors.apply();
            editors.apply();
            startActivity(new Intent(this, Ajouter_Photo_Produit.class));
        } else {
            duree_garentie.setError("Champ obligatoire");
        }

    }

//    private void addMonth(String dureegrantie) {
//
//        //Duree = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)+cal.get(Calendar.YEAR)+cal.get(Calendar.MONTH));
//        //String dt = Dateeachat;  // Start date
//        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        cal.getInstance();
//
//        cal.add(Calendar.MONTH, Integer.parseInt(dureegrantie)); // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
//
//
//        cal.getTime();
//        Toast.makeText(this, "" + cal.getTime(), Toast.LENGTH_SHORT).show();
//       /* SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
//        Dfin = sdf1.format(c.getTime());
//        Toast.makeText(this, ""+Dfin, Toast.LENGTH_SHORT).show();*/
//
//    }

    public void return_ajou_date_achat(View view) {
        startActivity(new Intent(this, Date_achat.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void btn_efface(View view) {
        String Text = duree_garentie.getText().toString();
        if (Text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            duree_garentie.setText("");
        }


    }

}
