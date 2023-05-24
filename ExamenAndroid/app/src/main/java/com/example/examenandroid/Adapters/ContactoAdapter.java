package com.example.examenandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter{

    private List<Contacto> contactos;

    public ContactoAdapter(List<Contacto> contactos) {
        this.contactos = contactos;
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

        String nombre = contactos.get(position).getNombre();
        String numero = contactos.get(position).getNumber();
        String imagen = contactos.get(position).getFotito();
        View view = holder.itemView;

        TextView txtName = view.findViewById(R.id.NombreText);
        TextView txtNumero = view.findViewById(R.id.NumeroText);
        ImageView imageView = view.findViewById(R.id.imgCon);
        txtName.setText(nombre);
        txtNumero.setText(numero);

        Picasso.get().load(imagen).into(imageView);
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
