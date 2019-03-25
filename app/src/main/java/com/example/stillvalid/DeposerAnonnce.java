package com.example.stillvalid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeposerAnonnce extends AppCompatActivity {
    String description_anc, categorie_anc, titre_anc, prix_anc, ville_anc, numtel_anc, email_anc;
    ImageView btn_menu;
    Spinner categorie;
    EditText annonce, description_annonce, prix, ville, email, tel;
    ImageView photoprod;
    String id_produit, Id_user;
    SharedPreferences prefs, prefss;
    ArrayAdapter<String> Adapter;
    private static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_IMAGE = 300;
    public static final int REQUEST_IMAGE_imp = 100;
    Bitmap bitmapContaratimp;
    static Bitmap PHOTO;
    Uri imageContratimport;
    ArrayList<String> List_Categorie = new ArrayList<>();
    public static final String CATEGORIE = "categorie";
    public static final String DESCRIPTION = "description";
    public static final String TITRE = "titre";
    public static final String PRIX = "prix";
    public static final String USER_ID = "user_id";
    public static final String VILLE = "ville";
    public static final String TELEPHONE = "numtel";
    public static final String EMAIL = "email";
    public static final String PHOTOPRODUIT = "photoProduit";

    public static final String DATA_URL = "http://192.168.1.18/StillValid/GetALLCategorie.php";
    public String url = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";
    public String Deposer_annonce = "http://192.168.1.18/StillValid/DeposeAnnonce.php?id_produit=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer_anonnce);

        categorie = findViewById(R.id.edit_categorie);
        annonce = findViewById(R.id.edit_annonce);
        description_annonce = findViewById(R.id.edit_annonce);
        prix = findViewById(R.id.edit_prix);
        ville = findViewById(R.id.edit_ville);
        email = findViewById(R.id.edit_email);
        tel = findViewById(R.id.btn_tel);
        photoprod = findViewById(R.id.still_valid1);
        btn_menu = findViewById(R.id.menu);


        photoprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEPOSERPHOTO();
            }
        });

        prefs = getSharedPreferences("login", MODE_PRIVATE);

        Id_user = prefs.getString("id", null);
        prefss = getSharedPreferences("mesproduit", MODE_PRIVATE);

        String restoredid = prefss.getString("Id_Produit", null);



        if (restoredid != null) {
            id_produit = restoredid;
            loadproduit();
            Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
        }
        checkpermission();


        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Categorie.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Categorie);
                    categorie.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeposerAnonnce.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(request2);
        categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorie_anc = Adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DeposerAnonnce.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    private void loadproduit() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_produit, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Picasso.get()
                            .load(response.getJSONObject(0).getString("photo"))
                            .resize(400, 500)
                            .into(photoprod);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public void valid_Deposer_annonce(View view) {
        categorie_anc = categorie.getSelectedItem().toString();
        description_anc = description_annonce.getText().toString().trim();
        titre_anc = annonce.getText().toString().trim();
        prix_anc = prix.getText().toString().trim();
        ville_anc = ville.getText().toString().trim();
        numtel_anc = tel.getText().toString().trim();
        email_anc = email.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(DeposerAnonnce.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Deposer_annonce, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Log.i("Myresponse", "" + response);
                    Toast.makeText(DeposerAnonnce.this, "" + response, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(DeposerAnonnce.this, "errr", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Mysmart", "" + error);
                Toast.makeText(DeposerAnonnce.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put(CATEGORIE, categorie_anc);
                param.put(DESCRIPTION, description_anc);
                param.put(TITRE, titre_anc);
                param.put(USER_ID, Id_user);
                param.put(VILLE, ville_anc);
                param.put(TELEPHONE, numtel_anc);
                param.put(EMAIL, email_anc);
                param.put(PHOTOPRODUIT, getStringImage(Ajouter_photo_contrat.PHOTO));


                return param;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }
    private void DEPOSERPHOTO(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_imp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_imp && resultCode == RESULT_OK) {
            imageContratimport = data.getData();
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageContratimport);
                getRealPathFromURI(imageContratimport);
                bitmapContaratimp = rotationImage(thumbnail, getRealPathFromURI(imageContratimport));


                photoprod.setImageBitmap(bitmapContaratimp);
                PHOTO = bitmapContaratimp;



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public Bitmap rotationImage(Bitmap bitmap, String imageUri) throws IOException {
        ExifInterface exifInterface = new ExifInterface(imageUri);
        int oreintation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (oreintation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);
            default:
                return bitmap;
        }
    }

    private Bitmap flip(Bitmap bitmap, boolean horizontal, boolean verticale) {
        Matrix matrix = new Matrix();
        matrix.postScale(horizontal ? -1 : 1, verticale ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public String getStringImage(Bitmap bitmap) {
        Log.i("MyHitesh", "" + bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }
    /*public void btn_efface(View view) {
        String txtannonce = annonce.getText().toString();
        String txt_desc_ann = description_annonce.getText().toString();
        String txt_prix = prix.getText().toString();
        String txt_ville = ville.getText().toString();
        String txt_email = email.getText().toString();
        if (txtannonce.isEmpty() && (txt_desc_ann.isEmpty()) && (txt_prix.isEmpty()) && (txt_ville.isEmpty()) && (txt_email.isEmpty())) {
            Toast.makeText(getApplicationContext(), "Already Empty!!!", Toast.LENGTH_SHORT);
        } else {
            annonce.setText("");
            description_annonce.setText("");
            prix.setText("");
            ville.setText("");
            email.setText("");
        }
    }*/
}