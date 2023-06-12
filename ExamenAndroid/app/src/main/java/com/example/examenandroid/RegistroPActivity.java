package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.Pokemon;
import com.example.examenandroid.Service.ContactoService;
import com.example.examenandroid.Service.PokemonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroPActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pactivity);

        Button registrarP = findViewById(R.id.btnRegistrarP);
        Button volverP = findViewById(R.id.btnVolverP);

        EditText regNom = findViewById(R.id.etNombreP);
        EditText regTipo = findViewById(R.id.etTipoP);
        EditText regFot = findViewById(R.id.etPhotoP);

        volverP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(RegistroPActivity.this, MenuPActivity.class);
                startActivity(intent);
            }
        });

        registrarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = regNom.getText().toString();
                String tipo = regTipo.getText().toString();
                String pokedex = regFot.getText().toString();
                String imagen = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + regFot.getText().toString() + ".png";

                if (!nombre.isEmpty() && !imagen.isEmpty() && !tipo.isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PokemonService service = retrofit.create(PokemonService.class);

                    Pokemon pok = new Pokemon();
                    pok.setNombre(nombre);
                    pok.setPokedex(pokedex);
                    pok.setTipo(tipo);
                    pok.setImagen(imagen);

                    Call<Pokemon> call = service.create(pok);

                    call.enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                            Intent intent =  new Intent(RegistroPActivity.this, MenuPActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Pokemon> call, Throwable t) {

                        }
                    });

                    Toast.makeText(RegistroPActivity.this, "Pokemon agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroPActivity.this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}