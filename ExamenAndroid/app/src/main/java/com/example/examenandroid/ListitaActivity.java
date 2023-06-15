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

import com.example.examenandroid.Adapters.PaisajeAdapter;
import com.example.examenandroid.Clases.Paisaje;
import com.example.examenandroid.Service.PaisajeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListitaActivity extends AppCompatActivity {

    private List<Paisaje> paisajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listita);

        ComponentName callingActivity = getCallingActivity();

        Button regresarC = findViewById(R.id.btnRegresar);
        regresarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListitaActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PaisajeService service = retrofit.create(PaisajeService.class);


        Call<List<Paisaje>> call = service.getAllUser();
        call.enqueue(new Callback<List<Paisaje>>() {
            @Override
            public void onResponse(Call<List<Paisaje>> call, Response<List<Paisaje>> response) {
                if (response.isSuccessful()) {
                    paisajes = response.body();
                    PaisajeAdapter adapter = new PaisajeAdapter(paisajes, ListitaActivity.this);

                    RecyclerView rvLista =  findViewById(R.id.rvListaSimple);
                    rvLista.setLayoutManager(new LinearLayoutManager(ListitaActivity.this));
                    rvLista.setAdapter(adapter);

                } else {
                    int statusCode = response.code();
                    String errorMessage = "Error: " + statusCode;

                    Toast.makeText(ListitaActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Paisaje>> call, Throwable t) {

            }
        });


    }
}