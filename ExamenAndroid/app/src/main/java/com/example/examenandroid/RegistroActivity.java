package com.example.examenandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.GuardarContactos;
import com.example.examenandroid.Service.ContactoService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registrarC = findViewById(R.id.btnRegistrar);
        Button volverC = findViewById(R.id.btnVolver);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGaleria);
        ivAvatar = findViewById(R.id.ivAvatar);

        EditText regNom = findViewById(R.id.etNombre);
        EditText regNum = findViewById(R.id.etNumero);
        EditText regFot = findViewById(R.id.etPhoto);

        volverC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(RegistroActivity.this, ListitaActivity.class);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOpenCamera();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                else {
                    String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 2000);
                }
            }
        });


        registrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = regNom.getText().toString();
                String numero = regNum.getText().toString();
                String imagen = regFot.getText().toString();

                if (!nombre.isEmpty() && !imagen.isEmpty() && !numero.isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ContactoService service = retrofit.create(ContactoService.class);

                    Contacto con = new Contacto();
                    con.setNombre(nombre);
                    con.setNumber(numero);
                    con.setFotito(imagen);

                    Call<Contacto> call = service.create(con);

                    call.enqueue(new Callback<Contacto>() {
                                 @Override
                                 public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                                     Intent intent =  new Intent(RegistroActivity.this, ListitaActivity.class);
                                     startActivity(intent);
                                     finish();
                                 }

                                 @Override
                                 public void onFailure(Call<Contacto> call, Throwable t) {

                                 }
                             });

                            Toast.makeText(RegistroActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroActivity.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(photo);
            System.out.println(photo + " + tmre");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            System.out.println(base64Image + " + tmre4");

        }

        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close(); // close cursor

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ivAvatar.setImageBitmap(bitmap);
            System.out.println(bitmap + " + tmre2");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            System.out.println(base64Image + " + tmre3");

        }

    }


    private void handleOpenCamera() {
        if(checkSelfPermission(Manifest.permission.CAMERA)  == PackageManager.PERMISSION_GRANTED)
        {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara");
            abrirCamara();
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando");
            String[] permissions = new String[] {Manifest.permission.CAMERA};
            requestPermissions(permissions, 1000);
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_GALLERY_REQUEST);
    }

}