package com.example.stillvalid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Inscription extends AppCompatActivity {
    Button bregister,blogin;
    EditText etName, etemail, etpassword;
    String email,password,name;
    String URL="http://192.168.1.21/stillvalid/signup.php";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        etName = (EditText) findViewById(R.id.etName);
        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);
    }

    public void login_login(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void register_register(View view) {
        name=etName.getText().toString().trim();
        email=etemail.getText().toString().trim();
        password=etpassword.getText().toString().trim();
        if (name.equals("") || email.equals("") || password.equals("")) {

            Toast.makeText(Inscription.this, "All Fields Are Compulsory", Toast.LENGTH_SHORT);
        }
        else {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(!response.isEmpty()){
                        Toast.makeText(Inscription.this, response, Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(Register.this,Home.class);
                        //startActivity(intent);
                        //Toast.makeText(MainActivity.this, "tttttt", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Inscription.this, "errr", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Inscription.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>params=new HashMap<String, String>();
                    params.put(NAME,name);
                    params.put(EMAIL,email);
                    params.put(PASSWORD,password);
                    return params;
                }
            };

            requestQueue.add(stringRequest);

        }
    }
}

