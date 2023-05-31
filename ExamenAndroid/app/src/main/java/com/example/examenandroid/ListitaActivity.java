package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.examenandroid.Adapters.AnimeAdapter;
import com.example.examenandroid.Adapters.ContactoAdapter;
import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.GuardarContactos;
import com.example.examenandroid.Service.ContactoService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListitaActivity extends AppCompatActivity {

    private List<Contacto> contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listita);

        ComponentName callingActivity = getCallingActivity();

        contactos = ((GuardarContactos) getApplicationContext()).getContactosList();


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



        ContactoService service = retrofit.create(ContactoService.class);

        Call<List<Contacto>> call = service.getAllUser();

        call.enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(Call<List<Contacto>> call, Response<List<Contacto>> response) {
                if (response.isSuccessful()) {
                    contactos = response.body();
                    // Hacer algo con la lista de contactos
                    // Por ejemplo, imprimir los nombres de los contactos
                    for (Contacto contacto : contactos) {
                        System.out.println(contacto.getNombre());
                    }
                } else {
                    // La respuesta no fue exitosa
                    // Manejar el código de error, si es necesario
                    int statusCode = response.code();
                    // Hacer algo en caso de error
                }
            }

            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {

            }
        });

        ContactoAdapter adapter = new ContactoAdapter(contactos, this);

        RecyclerView rvLista =  findViewById(R.id.rvListaSimple);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(adapter);
    }

    private List<Contacto> data() {

        Contacto c0 = new Contacto("Victor", "961987322", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c0);
        Contacto c1 = new Contacto("Omar", "987456321", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c1);
        Contacto c2 = new Contacto("Felix", "958741623", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c2);
        Contacto c3 = new Contacto("Luis", "951786324", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c3);
        Contacto c4 = new Contacto("Critiano", "962587413", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c4);
        Contacto c5 = new Contacto("Lionel", "947851236", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c5);
        Contacto c6 = new Contacto("Sergio", "912365478", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c6);
        Contacto c7 = new Contacto("Giampiere", "974123658", "https://intranet.upn.edu.pe/wsfoto/Foto/SmVJa3ZqcE12OWNSdGR2dE1ab0dhUT09/Student");
        contactos.add(c7);


        return contactos;
    }
}