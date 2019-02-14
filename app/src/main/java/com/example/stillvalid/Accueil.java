package com.example.stillvalid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear=calendar.get(calendar.YEAR);
        String str =Integer.toString(currentYear);
        TextView textView = findViewById(R.id.bas);
        textView.setText("COPYRGHIT StillValid All RIGHTS RESERVED - " + str );

    }
}
