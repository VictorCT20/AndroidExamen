package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenandroid.Clases.Pokemon;
import com.example.examenandroid.Service.PokemonService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallesPActivity extends AppCompatActivity {

    private Pokemon pokemon = new Pokemon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pactivity);

        int position = getIntent().getIntExtra("position", 0);

        Button regresarP = findViewById(R.id.buttonD);
        TextView id = findViewById(R.id.textDId);
        TextView nombre = findViewById(R.id.textDNombre);
        TextView tipo = findViewById(R.id.textDTipo);
        ImageView imageView = findViewById(R.id.imageD);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonService service = retrofit.create(PokemonService.class);

        Call<Pokemon> call = service.findUser(position);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                pokemon = response.body();
                id.setText(pokemon.getPokedex());
                nombre.setText(pokemon.getNombre());
                tipo.setText(pokemon.getTipo());
                Picasso.get().load(pokemon.getImagen()).into(imageView);
                System.out.println("pokemon:" + pokemon.getNombre());
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

        regresarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetallesPActivity.this, MostrarPActivity.class);
                startActivity(intent);
            }
        });

    }
}