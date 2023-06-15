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

import com.example.examenandroid.Clases.Paisaje;
import com.example.examenandroid.EditarActivity;
import com.example.examenandroid.MostrarActivity;
import com.example.examenandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaisajeAdapter extends RecyclerView.Adapter{

    private List<Paisaje> paisajes;

    private Context context;

    public PaisajeAdapter(List<Paisaje> paisajes, Context context) {
        this.paisajes = paisajes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contacto_string, parent, false);
        PaisajeAdapter.NameViewHolder viewHolder = new PaisajeAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        int id = Integer.parseInt(paisajes.get(position).getId());
        System.out.println("numero:" + id);
        String nombre = paisajes.get(position).getNombre();
        String imagen = paisajes.get(position).getFotito();
        View view = holder.itemView;

        TextView txtName = view.findViewById(R.id.NombreTextP);
        ImageView imageView = view.findViewById(R.id.imgCon);
        Button bttnMostrar = view.findViewById(R.id.bttnMostrar);
        //Button bttnEditar = view.findViewById(R.id.bttnEditar);
        //Button bttnEliminar = view.findViewById(R.id.bttnEliminar);
        txtName.setText(nombre);

        Picasso.get().load(imagen).into(imageView);

        bttnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, EditarActivity.class);
                intent.putExtra("position", id);
                context.startActivity(intent);
            }
        });
        /*bttnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ContactoService service = retrofit.create(ContactoService.class);

                Call<Void> call = service.delete(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(context, ListitaActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });*/

        bttnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir el nuevo Activity
                Intent intent =  new Intent(context, MostrarActivity.class);
                intent.putExtra("position", id);
                context.startActivity(intent);
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
        return paisajes.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
