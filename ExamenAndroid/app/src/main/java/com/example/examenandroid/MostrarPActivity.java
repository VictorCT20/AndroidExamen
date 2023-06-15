package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examenandroid.Adapters.PokemonAdapter;
import com.example.examenandroid.Clases.Pokemon;
import com.example.examenandroid.Service.PokemonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarPActivity extends AppCompatActivity {

    private List<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_pactivity);

        Button regresarP = findViewById(R.id.btnRegresarP);

        regresarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarPActivity.this, MenuPActivity.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);

        Call<List<Pokemon>> call = service.getAllUser();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    pokemons = response.body();
                    PokemonAdapter adapter = new PokemonAdapter(pokemons, MostrarPActivity.this);

                    RecyclerView rvLista =  findViewById(R.id.rvListaPokemon);
                    rvLista.setLayoutManager(new LinearLayoutManager(MostrarPActivity.this));
                    rvLista.setAdapter(adapter);
                }else {
                    int statusCode = response.code();
                    String errorMessage = "Error: " + statusCode;

                    Toast.makeText(MostrarPActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }
        });


    }
}