package com.example.examenandroid.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter{

    private List<Contacto> contactos;

    private Context context;

    public ContactoAdapter(List<Contacto> contactos, Context context) {
        this.contactos = contactos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contacto_string, parent, false);
        ContactoAdapter.NameViewHolder viewHolder = new ContactoAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        System.out.println("numero:" + position);

        String nombre = contactos.get(position).getNombre();
        String numero = contactos.get(position).getNumber();
        String imagen = contactos.get(position).getFotito();
        View view = holder.itemView;

        TextView txtName = view.findViewById(R.id.NombreText);
        TextView txtNumero = view.findViewById(R.id.NumeroText);
        ImageView imageView = view.findViewById(R.id.imgCon);
        Button bttnLlamar = view.findViewById(R.id.bttnLlamar);
        txtName.setText(nombre);
        txtNumero.setText(numero);

        Picasso.get().load(imagen).into(imageView);

        bttnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialer(numero);
            }
        });

    }
    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
