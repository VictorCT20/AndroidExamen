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

import java.util.ArrayList;
import java.util.List;

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
                    Contacto contacto = new Contacto(nombre, numero, imagen);
                    ((GuardarContactos) getApplicationContext()).setContactosList(contacto);
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