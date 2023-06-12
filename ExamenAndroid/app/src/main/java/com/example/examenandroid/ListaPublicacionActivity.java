package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examenandroid.Adapters.ContactoAdapter;
import com.example.examenandroid.Adapters.PublicacionAdapter;
import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.Publicacion;
import com.example.examenandroid.Service.ContactoService;
import com.example.examenandroid.Service.PublicacionService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaPublicacionActivity extends AppCompatActivity {

    private List<Publicacion> publicaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_publicacion);

        ComponentName callingActivity = getCallingActivity();

        Button bttnRegresar = findViewById(R.id.btnRegistrarNPu);

        bttnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ListaPublicacionActivity.this, RegistrarPublicacionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicacionService service = retrofit.create(PublicacionService.class);

        Call<List<Publicacion>> call = service.getAllUser();
        call.enqueue(new Callback<List<Publicacion>>() {
            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if (response.isSuccessful()) {
                    publicaciones = response.body();
                    PublicacionAdapter adapter = new PublicacionAdapter(publicaciones, ListaPublicacionActivity.this);

                    RecyclerView rvLista =  findViewById(R.id.rvListaPublicaciones);
                    rvLista.setLayoutManager(new LinearLayoutManager(ListaPublicacionActivity.this));
                    rvLista.setAdapter(adapter);

                } else {
                    int statusCode = response.code();
                    String errorMessage = "Error: " + statusCode;

                    Toast.makeText(ListaPublicacionActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {

            }
        });

    }
}