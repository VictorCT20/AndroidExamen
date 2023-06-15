package com.example.examenandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examenandroid.Clases.Paisaje;
import com.example.examenandroid.Service.PaisajeService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private ImageView ivAvatar;
    private String fotoEnBase64;
    private Bitmap photo;
    private String img;
    TextView regLon, regLat, regFot;
    double latitud, longitud;
    private LocationManager mlocationManager;
    String imR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registrarC = findViewById(R.id.btnRegistrar);
        Button volverC = findViewById(R.id.btnVolver);
        Button btnCamera = findViewById(R.id.btnCamera);
        //Button btnCoord = findViewById(R.id.btnCoordenadas);
        ivAvatar = findViewById(R.id.ivAvatar);

        EditText regNom = findViewById(R.id.etNombre);
        /*regFot = findViewById(R.id.etPhoto);
        regLat = findViewById(R.id.tvLatitud);
        regLon = findViewById(R.id.tvLongitud);
*/
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
                obtenerCoordenadas();
            }
        });




        registrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = regNom.getText().toString();
                String lat = latitud + "";
                String lon = longitud + "";



                if (!nombre.isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PaisajeService service = retrofit.create(PaisajeService.class);

                    Paisaje con = new Paisaje();
                    con.setNombre(nombre);
                    con.setFotito(img);
                    con.setLatitud(lat);
                    con.setLongitud(lon);

                    Call<Paisaje> call = service.create(con);

                    call.enqueue(new Callback<Paisaje>() {
                                 @Override
                                 public void onResponse(Call<Paisaje> call, Response<Paisaje> response) {

                                     Intent intent =  new Intent(RegistroActivity.this, ListitaActivity.class);
                                     startActivity(intent);
                                     finish();
                                 }

                                 @Override
                                 public void onFailure(Call<Paisaje> call, Throwable t) {
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

            PaisajeService.ImageToSave imageToSave = new PaisajeService.ImageToSave(fotoEnBase64);

            PaisajeService imageService = imgRetro.create(PaisajeService.class);

            Call<PaisajeService.ImageResponse> imgC = imageService.saveImage(imageToSave);

            imgC.enqueue(new Callback<PaisajeService.ImageResponse>() {
                @Override
                public void onResponse(Call<PaisajeService.ImageResponse> call, Response<PaisajeService.ImageResponse> response) {
                    if(response.isSuccessful()){
                        System.out.println(response.body().getUrl() + " + 2");
                        img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                    }
                    else
                        Log.i("MAIN_APP", "No se subió");
                }

                @Override
                public void onFailure(Call<PaisajeService.ImageResponse> call, Throwable t) {

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

                PaisajeService.ImageToSave imageToSave = new PaisajeService.ImageToSave(fotoEnBase64);

                PaisajeService imageService = imgRetro.create(PaisajeService.class);

                Call<PaisajeService.ImageResponse> imgC = imageService.saveImage(imageToSave);

                imgC.enqueue(new Callback<PaisajeService.ImageResponse>() {
                    @Override
                    public void onResponse(Call<PaisajeService.ImageResponse> call, Response<PaisajeService.ImageResponse> response) {
                        if(response.isSuccessful()){
                            System.out.println(response.body().getUrl());
                            img = "https://demo-upn.bit2bittest.com" + response.body().getUrl();
                        }
                        else
                            System.out.println("No subio");
                    }

                    @Override
                    public void onFailure(Call<PaisajeService.ImageResponse> call, Throwable t) {

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

    void obtenerCoordenadas(){

        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    System.out.println(latitud + " - " + longitud);
                    Log.i("MAIN_APP", "Latitud" + latitud);
                    Log.i("MAIN_APP", "Longitud" + longitud);
                    mlocationManager.removeUpdates(this);
                }
            };

            mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
        }
        else{
            String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
            Log.i("MAIN_APP", "No hay permisos pa esta webada");
            requestPermissions(permissions, 1000);
        }
    }



}