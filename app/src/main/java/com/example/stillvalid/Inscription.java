package com.example.stillvalid;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class Inscription extends AppCompatActivity {
    Config config;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +        //at least 1 digit
                    //"(?=.*[a-z])" +        //at least 1 lower case letter
                    //"(?=.*[A-Z])" +        //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +       //any letter
                    //"(?=.*[@#$%^&+=])" +   //at least 1 special character
                    //"(?=\\S+$)" +            //no white spaces
                    ".{4,}" +                //at least 4 characters
                    "$");
    Button bregister, blogin;
    EditText etName, etemail, etpassword;
    String email, password, name;

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        etName = findViewById(R.id.etName);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
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

    private boolean validateUsername() {
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
    }

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

    public void login_login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void register_register(View view) {
        name = etName.getText().toString().trim();
        email = etemail.getText().toString().trim();
        password = etpassword.getText().toString().trim();
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.INSCRIPTION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.isEmpty()) {
                        Toast.makeText(Inscription.this, response, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Inscription.this, "errr", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Inscription.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(NAME, name);
                    params.put(EMAIL, email);
                    params.put(PASSWORD, password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);

        }
    }
}

