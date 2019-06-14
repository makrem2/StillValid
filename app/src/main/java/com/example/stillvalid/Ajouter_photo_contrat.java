package com.example.stillvalid;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Ajouter_photo_contrat extends AppCompatActivity {
    ImageView btn_menu, prendre_photocontrat, import_photocontrat;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    ProgressDialog progressDialog;
    private static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_IMAGE = 300;
    public static final int REQUEST_IMAGE_imp = 100;
    Bitmap bitmapContaratimp, bitmapContratcam;
    static Bitmap PHOTO;
    Uri imageContratimport, imageContratcam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_photo_contrat);

        checkpermission();
        prendre_photocontrat = findViewById(R.id.img_prendre_photo_contrat);
        import_photocontrat = findViewById(R.id.img_import_photo_contrat);

        prefs = getSharedPreferences("ajoutercontart", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Ajouter_photo_contrat.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        prendre_photocontrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PRENDREPHOTO();
            }
        });
        import_photocontrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IMPORTPHOTO();
            }
        });

    }


    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
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

    private void PRENDREPHOTO() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageContratcam = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageContratcam);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void IMPORTPHOTO() {
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
                String realuri = getRealPathFromURI(imageContratimport);
                bitmapContaratimp = rotationImage(thumbnail, getRealPathFromURI(imageContratimport));
                import_photocontrat.setImageBitmap(bitmapContaratimp);
                PHOTO = bitmapContaratimp;
                editors.putString("Photo_Contrat", realuri);
                editors.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageContratcam);
                String realurl = getRealPathFromURI(imageContratcam);
                bitmapContratcam = rotationImage(thumbnail, realurl);
                prendre_photocontrat.setImageBitmap(bitmapContratcam);
                PHOTO = bitmapContratcam;
                editors.putString("Photo_Contrat", realurl);
                editors.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void valid_photo_cont(View view) {
        if (Ajouter_photo_contrat.PHOTO != null) {
            Intent intent = new Intent(getApplicationContext(), Recapulatif_contrat.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "choisissez une image", Toast.LENGTH_SHORT).show();
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void return_date_echance(View view) {
        startActivity(new Intent(this, Date_echence.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
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




    public void LISTE_DES_REMINDERS(MenuItem item) {

        startActivity(new Intent(this, MesProduits.class));
    }

    public void AJOUTER_UN_REMINDER(MenuItem item) {

        startActivity(new Intent(this, Ajouter_Produits.class));
    }

    public void BOUTIQUE(MenuItem item) {

        startActivity(new Intent(this, Boutique.class));
    }

    public void DECONNEXION(MenuItem item) {
        progressDialog = new ProgressDialog(Ajouter_photo_contrat.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        startActivity(new Intent(this, Login.class));
    }
}
