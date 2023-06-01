package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pactivity);

        Button reg = findViewById(R.id.bttnRegistrarP);
        Button mos = findViewById(R.id.bttnMostrarP);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MenuPActivity.this, RegistroPActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MenuPActivity.this, MostrarPActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}