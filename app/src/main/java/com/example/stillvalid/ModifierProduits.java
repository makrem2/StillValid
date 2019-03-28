package com.example.stillvalid;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ModifierProduits extends AppCompatActivity {
    ImageView btn_menu, profile_image, img_modif_fac, img_modif_art;
    SharedPreferences prefs;
    DatePickerDialog picker;
    Calendar myCalendar = Calendar.getInstance();
    EditText ensegineproduit, dureegrantie, nomproduit, date_achat;
    TextView modifierphoto, modifierfacture;
    Spinner marquee;
    ProgressDialog progressDialog;
    private static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_IMAGE_ARTICLE = 300;
    public static final int REQUEST_IMAGE_FACTURE = 100;
    Bitmap bitmapContaratart, bitmapContratfact;
    static Bitmap PHOTOARTICLE, PHOTOFACTURE;
    Uri imagefact, imageart;
    ArrayAdapter<String> Adapter;
    ArrayList<String> List_Marque = new ArrayList<>();
    List<marque> listMarques = new ArrayList<>();
    public static final String ID_PRODUIT = "id_produit";
    public static final String ENSEGINE = "enseigne";
    public static final String NOM_PRODUIT = "nom";
    public static final String GARANTIE = "garantie";
    public static final String DATE_ACHAT = "dAchat";
    public static final String MARQUE = "marque";
    public static final String SAV = "sav";
    public static final String DFIN = "dFin";
    public static final String MPHOTOFAC = "facture";
    public static final String MPHOTOART = "photo";

    String Ensegine, Nom, Garantie, DAchat, id_produit, Marque, sav, Date_Fin;

    public static final String DATA_URL = "http://192.168.1.18/StillValid/GetALLMarque.php";
    public static final String Modif_URL = "http://192.168.1.18/StillValid/Modifier_ProduitById.php";
    public String url = "http://192.168.1.18/StillValid/ProduitById.php?id_produit=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_produits);


        ensegineproduit = findViewById(R.id.edit_ensegine_achat);
        dureegrantie = findViewById(R.id.edit_garantie);
        nomproduit = findViewById(R.id.edit_produit);
        date_achat = findViewById(R.id.edit_date_achat);
        modifierphoto = findViewById(R.id.modifierphoto);
        modifierfacture = findViewById(R.id.modifierfacture);
        marquee = findViewById(R.id.sp_marque);
        profile_image = findViewById(R.id.profile_image);
        img_modif_fac = findViewById(R.id.modif_fact);
        img_modif_art = findViewById(R.id.modif_art);

        modifierfacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODIFPHOTOFACTURE();
            }
        });
        modifierphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODIFPHOTOARTICLE();
            }
        });
        checkpermission();


        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ModifierProduits.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });


        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        List_Marque.add(response.getJSONObject(i).getString("libelle"));
                        //sav.add(response.getJSONObject(i).getString("sav"));
                        listMarques.add(new marque(response.getJSONObject(i).getString("id"),
                                response.getJSONObject(i).getString("libelle"),
                                response.getJSONObject(i).getString("sav"),
                                response.getJSONObject(i).getString("support")));

                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, List_Marque);
                    marquee.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifierProduits.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(request2);
        marquee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Marque = Adapter.getItem(i);
                sav = listMarques.get(i).getSav();


                //Toast.makeText(ModifierProduits.this, Adapter.getItem(i) + "", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        prefs = getSharedPreferences("mesproduit", MODE_PRIVATE);
        String restoredid = prefs.getString("Id_Produit", null);
        if (restoredid != null) {
            id_produit = restoredid;
            //Toast.makeText(this, id_produit, Toast.LENGTH_SHORT).show();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id_produit, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        ensegineproduit.setText(response.getJSONObject(0).getString("enseigne"));
                        dureegrantie.setText(response.getJSONObject(0).getString("garantie"));
                        marquee.setSelection(TrouverIndice(response.getJSONObject(0).getString("marque")));
                        nomproduit.setText(response.getJSONObject(0).getString("nom"));
                        date_achat.setText(response.getJSONObject(0).getString("dAchat"));


                        Picasso.get()
                                .load(response.getJSONObject(0).getString("photo"))
                                .resize(400, 500)
                                .into(profile_image);


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
    }

    public void getDate(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        }, year, month, day);
        picker.show();
    }

    private void updateLabel() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        date_achat.setText(sdf.format(myCalendar.getTime()));
    }

    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }

    private void MODIFPHOTOFACTURE() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_FACTURE);
    }

    private void MODIFPHOTOARTICLE() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ARTICLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_FACTURE && resultCode == RESULT_OK) {
            imagefact = data.getData();
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imagefact);
                String facture = getRealPathFromURI(imagefact);
                bitmapContratfact = rotationImage(thumbnail, getRealPathFromURI(imagefact));
                img_modif_fac.setVisibility(View.VISIBLE);
                img_modif_fac.setImageBitmap(bitmapContratfact);
                PHOTOFACTURE = bitmapContratfact;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_ARTICLE && resultCode == RESULT_OK) {
            imageart = data.getData();
            try {
                Bitmap thumbnailart = MediaStore.Images.Media.getBitmap(getContentResolver(), imageart);
                String article = getRealPathFromURI(imageart);
                bitmapContaratart = rotationImage(thumbnailart, getRealPathFromURI(imageart));
                img_modif_art.setVisibility(View.VISIBLE);
                img_modif_art.setImageBitmap(bitmapContaratart);
                PHOTOARTICLE = bitmapContaratart;
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

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }

    public int TrouverIndice(String Marque) {
        int i = 0;
        for (String item : List_Marque) {
            if (item.equals(Marque)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    private boolean Valider() {
        boolean valide = true;
        if (Ensegine.isEmpty()) {
            ensegineproduit.setError("champs_obligatoir");
            valide = false;
        }
        if (Nom.isEmpty()) {
            nomproduit.setError("champs_obligatoir");
            valide = false;
        }
        if (Garantie.isEmpty()) {
            dureegrantie.setError("champs_obligatoir");
            valide = false;
        }
        if (DAchat.isEmpty()) {
            date_achat.setError("champs_obligatoir");
            valide = false;
        }
        return valide;
    }


    public void valid_Modif_prod(View view) {
        Ensegine = ensegineproduit.getText().toString().trim();
        Nom = nomproduit.getText().toString().trim();
        Garantie = dureegrantie.getText().toString().trim();
        Marque = marquee.getSelectedItem().toString();
        DAchat = date_achat.getText().toString().trim();

        Calendar cal = Calendar.getInstance();
        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        try {
            cal.setTime(sdf.parse(DAchat));
            cal.add(Calendar.MONTH, Integer.parseInt(Garantie));
            Date_Fin = sdf.format(cal.getTime());
            //Toast.makeText(this, "" + Date_Fin, Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Modif_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(ModifierProduits.this, response, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ModifierProduits.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifierProduits.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(ID_PRODUIT, id_produit);
                params.put(ENSEGINE, Ensegine);
                params.put(NOM_PRODUIT, Nom);
                params.put(GARANTIE, Garantie);
                params.put(MARQUE, Marque);
                params.put(SAV, sav);
                params.put(DATE_ACHAT, DAchat);
                params.put(MPHOTOFAC, getStringImage(PHOTOFACTURE));
                params.put(MPHOTOART, getStringImage(PHOTOARTICLE));
                params.put(DFIN, Date_Fin);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public String getStringImage(Bitmap bitmap) {
        Log.i("MyHitesh", "" + bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }
    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(ModifierProduits.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }


}

