package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.examenandroid.Adapters.ContactoAdapter;
import com.example.examenandroid.Clases.Contacto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ListitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listita);

        ContactoAdapter adapter = new ContactoAdapter(data());

        RecyclerView rvLista =  findViewById(R.id.rvListaSimple);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(adapter);

    }

    private List<Contacto> data() {
        List<Contacto> contactos = new ArrayList<>();
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