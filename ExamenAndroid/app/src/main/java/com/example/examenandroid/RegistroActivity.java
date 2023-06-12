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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class RegistroActivity extends AppCompatActivity {

    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private ImageView ivAvatar;
    private String fotoEnBase64;
    private Bitmap photo;
    private String img;
    String imR;

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



                if (!nombre.isEmpty() && !numero.isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ContactoService service = retrofit.create(ContactoService.class);

                    Contacto con = new Contacto();
                    con.setNombre(nombre);
                    con.setNumber(numero);
                    con.setFotito(img);

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
                                     System.out.println("fallo");
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
            photo = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(photo);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit imgRetro = new Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ContactoService.ImageToSave imageToSave = new ContactoService.ImageToSave(fotoEnBase64);

            ContactoService imageService = imgRetro.create(ContactoService.class);

            Call<ContactoService.ImageResponse> imgC = imageService.saveImage(imageToSave);

            imgC.enqueue(new Callback<ContactoService.ImageResponse>() {
                @Override
                public void onResponse(Call<ContactoService.ImageResponse> call, Response<ContactoService.ImageResponse> response) {
                    if(response.isSuccessful()){
                        System.out.println(response.body().getUrl() + " + 2");
                        img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                    }
                    else
                        Log.i("MAIN_APP", "No se subió");
                }

                @Override
                public void onFailure(Call<ContactoService.ImageResponse> call, Throwable t) {

                }
            });



        }

        if(requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivAvatar.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Retrofit imgRetro = new Retrofit.Builder()
                        .baseUrl("https://demo-upn.bit2bittest.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ContactoService.ImageToSave imageToSave = new ContactoService.ImageToSave(fotoEnBase64);

                ContactoService imageService = imgRetro.create(ContactoService.class);

                Call<ContactoService.ImageResponse> imgC = imageService.saveImage(imageToSave);

                imgC.enqueue(new Callback<ContactoService.ImageResponse>() {
                    @Override
                    public void onResponse(Call<ContactoService.ImageResponse> call, Response<ContactoService.ImageResponse> response) {
                        if(response.isSuccessful()){
                            System.out.println(response.body().getUrl());
                            img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                        }
                        else
                            System.out.println("No subio");
                    }

                    @Override
                    public void onFailure(Call<ContactoService.ImageResponse> call, Throwable t) {

                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


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