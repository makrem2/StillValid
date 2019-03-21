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
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.IOException;

public class Ajouter_Photo_Produit extends AppCompatActivity {
    ImageView btn_menu, prnd_photo_article, prnd_photo_facture;
    SharedPreferences prefs;
    SharedPreferences.Editor editors;
    private static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_IMAGE_ARTICLE = 300;
    public static final int REQUEST_IMAGE_FACTURE = 100;
    Bitmap bitmapContaratart, bitmapContratfact;
    static Bitmap PHOTOARTICLE, PHOTOFACTURE;
    Uri imagefact, imageart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__photo__produit);
        checkpermission();
        prnd_photo_article = findViewById(R.id.img_prender_article);
        prnd_photo_facture = findViewById(R.id.img_prender_facture);

        prefs = getSharedPreferences("Produit", MODE_PRIVATE);
        editors = prefs.edit();

        btn_menu = findViewById(R.id.menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Ajouter_Photo_Produit.this, btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu, popupMenu.getMenu());
                popupMenu.show();
            }
        });
        prnd_photo_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoArticle();
            }
        });
        prnd_photo_facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoFacture();
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

    private void PhotoArticle() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ARTICLE);
    }

    private void PhotoFacture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_FACTURE);
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
                prnd_photo_facture.setImageBitmap(bitmapContratfact);
                PHOTOFACTURE = bitmapContratfact;
                editors.putString("Photo_Facture", facture);
                editors.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_ARTICLE && resultCode == RESULT_OK) {
            imageart = data.getData();
            try {
                Bitmap thumbnailart = MediaStore.Images.Media.getBitmap(getContentResolver(), imageart);
                String article = getRealPathFromURI(imageart);
                bitmapContaratart = rotationImage(thumbnailart, getRealPathFromURI(imageart));
                prnd_photo_article.setImageBitmap(bitmapContaratart);
                PHOTOARTICLE = bitmapContaratart;
                editors.putString("Photo_Article", article);
                editors.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void valid_photo(View view) {
       if ((Ajouter_Photo_Produit.PHOTOARTICLE != null)&&(Ajouter_Photo_Produit.PHOTOFACTURE != null)) {
        Intent intent = new Intent(getApplicationContext(), recapitulatife_Produit.class);
        startActivity(intent);

              } else {
            Toast.makeText(this, "choisissez une image", Toast.LENGTH_SHORT).show();
         }
       // Toast.makeText(this, ""+PHOTOARTICLE+PHOTOFACTURE, Toast.LENGTH_SHORT).show();
    }

    public void return_ajou_ph_prod(View view) {
        startActivity(new Intent(this, Duree_garantie.class));
    }

    public void acueil(View view) {
        startActivity(new Intent(this, Accueil.class));
    }
}
