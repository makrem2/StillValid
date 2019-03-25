package com.example.stillvalid;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.View;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class modifier_contrat extends AppCompatActivity {
    private static final String TAG = "Date_echence";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_IMAGE = 300;
    public static final int REQUEST_IMAGE_imp = 100;
    ImageView btn_menu,image,photocontrat;
    Spinner Type_contrat;
    EditText Date_Echeance;
    TextView Modifer_contrat;
    String id_contrat,type_contrat,date_echeance;
    ArrayAdapter<String> Adapter;
    ArrayList<String> TypeContrat = new ArrayList<>();
    SharedPreferences prefscontart;
    SharedPreferences.Editor editors;
    Bitmap bitmapContaratimp;
    static Bitmap PHOTO;
    Uri imageContratimport;
    public static final String TYPECONTRAT = "type";
    public static final String DATEECHANCE = "dEcheance";
    public static final String IMPORT_PHOTO = "photo";
    public static final String DATA_URL = "http://192.168.1.18/StillValid/GetALLTypes.php";
    public String url = "http://192.168.1.18/StillValid/ContratById.php?id_contrat=";
    String modifcontrat = "http://192.168.1.18/StillValid/Modifier_contratById.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_contrat);
        checkpermission();
        image = findViewById(R.id.profile_image);
        Type_contrat=findViewById(R.id.sp_type_contrat);
        Date_Echeance = findViewById(R.id.edit_date_echeance);
        Modifer_contrat = findViewById(R.id.modifer_contrat);
        photocontrat = findViewById(R.id.img_cont_modif);


        Modifer_contrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODIFPHOTO();
            }
        });

        Date_Echeance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(modifier_contrat.this,
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
                Date_Echeance.setText(date);
            }
        };

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(modifier_contrat.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.show();
            }
        });



        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        TypeContrat.add(response.getJSONObject(i).getString("libelle"));
                    }
                    Adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, TypeContrat);
                    Type_contrat.setAdapter(Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(modifier_contrat.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        Type_contrat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter.getItem(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        prefscontart = getSharedPreferences("mescontart", MODE_PRIVATE);
        String restoredid = prefscontart.getString("Id_Contrat", null);

        if (restoredid != null) {
            id_contrat = restoredid;
            Toast.makeText(this, id_contrat, Toast.LENGTH_SHORT).show();

            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url + id_contrat, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        Date_Echeance.setText(response.getJSONObject(0).getString("dEcheance"));

                        Type_contrat.setSelection(TrouverIndice(response.getJSONObject(0).getString("type")));
                        Picasso.get()
                                .load(response.getJSONObject(0).getString("photo"))
                                .resize(400, 500)
                                .into(image);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue queue2 = Volley.newRequestQueue(this);
            queue2.add(request2);
        }
    }
    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
    }
    private void MODIFPHOTO(){
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

                photocontrat.setVisibility(View.VISIBLE);
                photocontrat.setImageBitmap(bitmapContaratimp);
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


    public void acueil (View view){
        startActivity(new Intent(this,Accueil.class));}

    public int TrouverIndice(String itemCt) {
        int i = 0;
        for (String item : TypeContrat) {
            if (item.equals(itemCt)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public void valid_modif_c(View view) {
        type_contrat =  Type_contrat .getSelectedItem().toString();
        date_echeance = Date_Echeance.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, modifcontrat+id_contrat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Toast.makeText(modifier_contrat.this, response, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(modifier_contrat.this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(modifier_contrat.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(TYPECONTRAT, type_contrat);
                params.put(DATEECHANCE, date_echeance);
                params.put(IMPORT_PHOTO, getStringImage(PHOTO));

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
}
