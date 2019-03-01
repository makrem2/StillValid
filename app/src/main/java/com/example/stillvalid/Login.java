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

public class Login extends AppCompatActivity {
    Button blogin, bregister;
    EditText etemail, etpassword;
    String email,password;
    public static final String LOGIN_URL = "http://192.168.1.21/stillvalid/connexion.php";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);

        blogin = (Button) findViewById(R.id.blogin);
        bregister = (Button) findViewById(R.id.bregister);
    }
    public void main_login(View view) {
        email=etemail.getText().toString().trim();
        password=etpassword.getText().toString().trim();
        if (email.equals("")||password.equals("")){
            Toast.makeText(Login.this,"Enter valid username and password", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest stringRequest=new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(!response.isEmpty()){
                        Intent intent = new Intent(Login.this,Accueil.class);
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "tttttt", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Login.this, "Adresse e-mail ou mot de passe invalide", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>params=new HashMap<String, String>();

                    params.put(KEY_EMAIL,email);
                    params.put(KEY_PASSWORD,password);
                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);

        }
    }

    public void main_register(View view) {
        Intent intent = new Intent(this,Inscription.class);
        startActivity(intent);
    }
}
