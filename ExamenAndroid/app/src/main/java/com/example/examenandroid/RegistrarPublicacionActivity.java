package com.example.examenandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.examenandroid.Clases.Publicacion;
import com.example.examenandroid.Service.PublicacionService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarPublicacionActivity extends AppCompatActivity {

    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private ImageView ivAvatar;
    private String img;
    private EditText regFot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_publicacion);

        Button btnregistrarC = findViewById(R.id.btnRegistrarPu);
        Button btnvolverC = findViewById(R.id.btnVolverPu);
        Button btnCamera = findViewById(R.id.btnCameraPu);
        Button btnGallery = findViewById(R.id.btnGaleriaPu);
        ivAvatar = findViewById(R.id.ivPhoto);

        EditText regDes = findViewById(R.id.etDescripcionPu);
        regFot = findViewById(R.id.etPhotoPu);


        btnvolverC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(RegistrarPublicacionActivity.this, ListaPublicacionActivity.class);
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
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                else {
                    String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, 2000);
                }
            }
        });

        btnregistrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = regDes.getText().toString();
                String foto = regFot.getText().toString();

                if (!descripcion.isEmpty() && !foto.isEmpty()){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PublicacionService service = retrofit.create(PublicacionService.class);

                    Publicacion publi = new Publicacion();
                    publi.setDescripcion(descripcion);
                    publi.setFoto(foto);
                    Call<Publicacion> call = service.create(publi);

                    call.enqueue(new Callback<Publicacion>() {
                        @Override
                        public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                            Intent intent =  new Intent(RegistrarPublicacionActivity.this, ListaPublicacionActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Publicacion> call, Throwable t) {
                            System.out.println("fallo");
                        }
                    });
                    Toast.makeText(RegistrarPublicacionActivity.this, "Publicacion agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistrarPublicacionActivity.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
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

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit imgRetro = new Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PublicacionService.ImageToSave imageToSave = new PublicacionService.ImageToSave(fotoEnBase64);

            PublicacionService imageService = imgRetro.create(PublicacionService.class);

            Call<PublicacionService.ImageResponse> imgC = imageService.saveImage(imageToSave);

            imgC.enqueue(new Callback<PublicacionService.ImageResponse>() {
                @Override
                public void onResponse(Call<PublicacionService.ImageResponse> call, Response<PublicacionService.ImageResponse> response) {
                    if(response.isSuccessful()){
                        System.out.println(response.body().getUrl() + " + 2");
                        img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                        regFot.setText(img);
                    }
                    else
                        Log.i("MAIN_APP", "No se subió");
                }

                @Override
                public void onFailure(Call<PublicacionService.ImageResponse> call, Throwable t) {

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
                String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Retrofit imgRetro = new Retrofit.Builder()
                        .baseUrl("https://demo-upn.bit2bittest.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PublicacionService.ImageToSave imageToSave = new PublicacionService.ImageToSave(fotoEnBase64);

                PublicacionService imageService = imgRetro.create(PublicacionService.class);

                Call<PublicacionService.ImageResponse> imgC = imageService.saveImage(imageToSave);

                imgC.enqueue(new Callback<PublicacionService.ImageResponse>() {
                    @Override
                    public void onResponse(Call<PublicacionService.ImageResponse> call, Response<PublicacionService.ImageResponse> response) {
                        if(response.isSuccessful()){
                            System.out.println(response.body().getUrl() + " + 1");
                            img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                            regFot.setText(img);
                        }
                        else
                            Log.i("MAIN_APP", "No se subió");
                    }

                    @Override
                    public void onFailure(Call<PublicacionService.ImageResponse> call, Throwable t) {

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