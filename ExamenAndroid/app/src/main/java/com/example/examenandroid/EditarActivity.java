package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Service.ContactoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarActivity extends AppCompatActivity {

    private Contacto con = new Contacto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);


        int position = getIntent().getIntExtra("position", 0);
        System.out.println("llama:" + position);


        Button regresarC = findViewById(R.id.btnERegresar);
        Button editarB = findViewById(R.id.btnEditar);

        EditText regNom = findViewById(R.id.etENombre);
        EditText regNum = findViewById(R.id.etENumero);
        EditText regFot = findViewById(R.id.etEPhoto);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ContactoService service = retrofit.create(ContactoService.class);

        Call<Contacto> call = service.findUser(position);

        call.enqueue(new Callback<Contacto>() {
            @Override
            public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                con = response.body();
                regNom.setText(con.getNombre());
                regNum.setText(con.getNumber());
                regFot.setText(con.getFotito());
                System.out.println("con:" + con.getNombre());
            }

            @Override
            public void onFailure(Call<Contacto> call, Throwable t) {
                Log.e("API Error", "Error en la llamada a la API: " + t.getMessage());
            }
        });


        editarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = regNom.getText().toString();
                String numero = regNum.getText().toString();
                String imagen = regFot.getText().toString();

                if (!nombre.isEmpty()) {

                    Contacto contacto = new Contacto(nombre, numero, imagen);

                    Call<Contacto> call1 = service.update(position, contacto);

                    call1.enqueue(new Callback<Contacto>() {
                        @Override
                        public void onResponse(Call<Contacto> call, Response<Contacto> response) {

                        }

                        @Override
                        public void onFailure(Call<Contacto> call, Throwable t) {

                        }
                    });

                    Toast.makeText(EditarActivity.this, "Usuario agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditarActivity.this, "Ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
                }

                Intent intent =  new Intent(EditarActivity.this, ListitaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regresarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(EditarActivity.this, ListitaActivity.class);
                startActivity(intent);
            }
        });

    }
}