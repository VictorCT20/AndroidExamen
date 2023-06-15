package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.examenandroid.Adapters.ComentarioAdapter;
import com.example.examenandroid.Adapters.ContactoAdapter;
import com.example.examenandroid.Adapters.PublicacionAdapter;
import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.Publicacion;
import com.example.examenandroid.Service.ContactoService;
import com.example.examenandroid.Service.PublicacionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComentariosActivity extends AppCompatActivity {

    private List<String> comentarios;
    private Publicacion pub = new Publicacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        int position = getIntent().getIntExtra("position", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PublicacionService service = retrofit.create(PublicacionService.class);

        Call<Publicacion> call = service.findUser(position);

        call.enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {

                pub = response.body();

                ComentarioAdapter adapter = new ComentarioAdapter(pub.getComentarios(), ComentariosActivity.this);

                RecyclerView rvLista =  findViewById(R.id.rvListaComentarios);
                rvLista.setLayoutManager(new LinearLayoutManager(ComentariosActivity.this));
                rvLista.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {

            }
        });

        Button bttnRegistrar = findViewById(R.id.buttonRegistrarComent);
        EditText newComen = findViewById(R.id.etNewComen);

        bttnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("tmre: " + newComen);
                String newCo = newComen.toString();
                comentarios.add(newCo);
                pub.setComentarios(comentarios);

                Call<Publicacion> call2 = service.update(position, pub);

                call2.enqueue(new Callback<Publicacion>() {
                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {

                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {

                    }
                });
            }
        });



    }
}