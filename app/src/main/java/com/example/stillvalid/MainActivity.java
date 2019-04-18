package com.example.stillvalid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          //progressBar.setVisibility(View.GONE);
                                          Intent intent = new Intent(getApplicationContext(), Login.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }, 4000


        );


    }

    private void init() {
        this.progressBar = findViewById(R.id.progressBar);
    }
}