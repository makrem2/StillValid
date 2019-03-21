package com.example.stillvalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    public static final String LOGIN_URL = "http://192.168.1.17/StillValid/login.php";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    String email,password;
    private EditText etemail;
    private EditText etpassword;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +        //at least 1 digit
                    //"(?=.*[a-z])" +        //at least 1 lower case letter
                    //"(?=.*[A-Z])" +        //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +       //any letter
                    //"(?=.*[@#$%^&+=])" +   //at least 1 special character
                    "(?=\\S+$)" +            //no white spaces
                    ".{4,}" +                //at least 4 characters
                    "$");

    private TextInputLayout textInputEmail;
    //private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword = findViewById(R.id.text_input_password);
        etemail =findViewById(R.id.txt_email);
        etpassword = findViewById(R.id.txt_password);
        prefs = getSharedPreferences("login", MODE_PRIVATE);
        editors = prefs.edit();
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

   /* private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }*/

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }



    public void main_login(View view) {
        email=etemail.getText().toString();
        password=etpassword.getText().toString();

        if (!validateEmail() | !validatePassword()) {
            return;
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()) {
                        try {
                            JSONArray produit = new JSONArray(response);
                            JSONObject produitobject = produit.getJSONObject(0);
                           // Toast.makeText(getApplicationContext(),produitobject.getString("id")+"", Toast.LENGTH_SHORT).show();
                            editors.putString("id", produitobject.getString("id"));
                            editors.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Login.this, Accueil.class);
                        startActivity(intent);

                       // Toast.makeText(getApplicationContext(),response+"", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, "Adresse e-mail ou mot de passe invalide", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(KEY_EMAIL, email);
                    params.put(KEY_PASSWORD, password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);

        }
    }

    public void main_register(View view) {
        Intent intent = new Intent(this, Inscription.class);
        startActivity(intent);
    }
}
