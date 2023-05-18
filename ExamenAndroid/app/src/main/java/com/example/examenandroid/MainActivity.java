package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int num1 = 0, num2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rnd = new Random();

        Button bttnAzar = findViewById(R.id.BttnCambio);
        Button bttnReset = findViewById(R.id.BttnReset);

        TextView Num1 = findViewById(R.id.Numero1);
        TextView Num2 = findViewById(R.id.Numero2);
        TextView Ganador = findViewById(R.id.Ganador);
        TextView Jugador = findViewById(R.id.JugadorName);

        bttnAzar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Jugador.getText().equals("Jugador 1")){
                    num1 = rnd.nextInt(10)+1;
                    Jugador.setText("Jugador 2");
                    bttnAzar.setText("Jugador 2");
                    Num1.setText(String.valueOf(num1));
                }
                else if(Jugador.getText().equals("Jugador 2")){
                    num2 = rnd.nextInt(10)+1;
                    Num2.setText(String.valueOf(num2));

                    Jugador.setText("Fin");
                    bttnAzar.setText("Fin");

                    if(num1>num2) Ganador.setText("Jugador 1");
                    else if(num1<num2) Ganador.setText("Jugador 2");
                    else Ganador.setText("Empate");

                }

            }
        });

        bttnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Num1.setText("0");
                Num2.setText("0");
                Jugador.setText("Jugador 1");
                bttnAzar.setText("Jugador 1");
                Ganador.setText("Ganador");
            }
        });


    }
}