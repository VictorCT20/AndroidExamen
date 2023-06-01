package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.GuardarContactos;
import com.example.examenandroid.Service.ContactoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registrarC = findViewById(R.id.btnRegistrar);

        EditText regNom = findViewById(R.id.etNombre);
        EditText regNum = findViewById(R.id.etNumero);
        EditText regFot = findViewById(R.id.etPhoto);



        registrarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = regNom.getText().toString();
                String numero = regNum.getText().toString();
                String imagen = regFot.getText().toString();

                if (!nombre.isEmpty()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ContactoService service = retrofit.create(ContactoService.class);

                    Contacto con = new Contacto();
                    con.setNombre(nombre);
                    con.setNumber(numero);
                    con.setFotito(imagen);

                    Call<Contacto> call = service.create(con);

                    call.enqueue(new Callback<Contacto>() {
                                     @Override
                                     public void onResponse(Call<Contacto> call, Response<Contacto> response) {

                                     }

                                     @Override
                                     public void onFailure(Call<Contacto> call, Throwable t) {

                                     }
                                 });

                            Toast.makeText(RegistroActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroActivity.this, "Ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
                }

                Intent intent =  new Intent(RegistroActivity.this, ListitaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}