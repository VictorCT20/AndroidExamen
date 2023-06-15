package com.example.examenandroid.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.Clases.Publicacion;
import com.example.examenandroid.ComentariosActivity;
import com.example.examenandroid.EditarActivity;
import com.example.examenandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;

public class PublicacionAdapter extends RecyclerView.Adapter{

    private List<Publicacion> publicaciones;

    private Context context;

    public PublicacionAdapter(List<Publicacion> publicaciones, Context context) {
        this.publicaciones = publicaciones;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_publicacion, parent, false);
        PublicacionAdapter.NameViewHolder viewHolder = new PublicacionAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int id = Integer.parseInt(publicaciones.get(position).getId());
        System.out.println("numero:" + id);
        String descripcion = publicaciones.get(position).getDescripcion();
        String imagen = publicaciones.get(position).getFoto();
        View view = holder.itemView;

        TextView txtDescr = view.findViewById(R.id.tvDescripcion);
        ImageView imageView = view.findViewById(R.id.imgPub);
        Button bttnComentar = view.findViewById(R.id.bttnComentar);

        txtDescr.setText(descripcion);
        Picasso.get().load(imagen).into(imageView);

        bttnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir el nuevo Activity
                Intent intent =  new Intent(context, ComentariosActivity.class);
                intent.putExtra("position", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }


    public class NameViewHolder extends RecyclerView.ViewHolder {
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
