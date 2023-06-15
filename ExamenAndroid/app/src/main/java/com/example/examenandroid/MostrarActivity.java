package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenandroid.Clases.Paisaje;
import com.example.examenandroid.Service.PaisajeService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarActivity extends AppCompatActivity {

    private Paisaje con = new Paisaje();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        int position = getIntent().getIntExtra("position", 0);

        Button regresarC = findViewById(R.id.btnMRegresar);
        Button mapaB = findViewById(R.id.btnMapa);

        ImageView image = findViewById(R.id.imagenMostrar);
        TextView regNom = findViewById(R.id.etENombreM);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PaisajeService service = retrofit.create(PaisajeService.class);

        Call<Paisaje> call = service.findUser(position);

        call.enqueue(new Callback<Paisaje>() {
            @Override
            public void onResponse(Call<Paisaje> call, Response<Paisaje> response) {
                con = response.body();
                regNom.setText(con.getNombre());
                Picasso.get().load(con.getFotito()).into(image);
                System.out.println("con:" + con.getNombre());
            }

            @Override
            public void onFailure(Call<Paisaje> call, Throwable t) {

            }
        });

        mapaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarActivity.this, MapsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        regresarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MostrarActivity.this, ListitaActivity.class);
                startActivity(intent);
            }
        });




    }
}