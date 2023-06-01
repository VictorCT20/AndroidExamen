package com.example.examenandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.Clases.Contacto;
import com.example.examenandroid.Clases.Pokemon;
import com.example.examenandroid.DetallesPActivity;
import com.example.examenandroid.EditarActivity;
import com.example.examenandroid.ListitaActivity;
import com.example.examenandroid.MostrarPActivity;
import com.example.examenandroid.R;
import com.example.examenandroid.Service.ContactoService;
import com.example.examenandroid.Service.PokemonService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonAdapter extends RecyclerView.Adapter{

    private List<Pokemon> pokemons;
    private Context context;

    public PokemonAdapter(List<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_pokemon, parent, false);
        PokemonAdapter.NameViewHolder viewHolder = new PokemonAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int id = Integer.parseInt(pokemons.get(position).getId());
        String nombre = pokemons.get(position).getNombre();
        String pokedex = pokemons.get(position).getPokedex();
        String tipo = pokemons.get(position).getTipo();
        String imagen = pokemons.get(position).getImagen();
        View view = holder.itemView;

        TextView txtID = view.findViewById(R.id.IdTextP);
        TextView txtName = view.findViewById(R.id.NombreTextP);
        TextView txtTipo = view.findViewById(R.id.TipoTextP);
        ImageView imageView = view.findViewById(R.id.imgPok);
        Button bttnDetalle = view.findViewById(R.id.bttnDetalleP);
        Button bttnEliminar = view.findViewById(R.id.bttnEliminarP);
        txtID.setText(pokedex);
        txtName.setText(nombre);
        txtTipo.setText(tipo);
        Picasso.get().load(imagen).into(imageView);


        bttnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://6477430d9233e82dd53b49f9.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PokemonService service = retrofit.create(PokemonService.class);

                Call<Void> call = service.delete(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(context, MostrarPActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        bttnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir el nuevo Activity
                Intent intent =  new Intent(context, DetallesPActivity.class);
                intent.putExtra("position", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
    public class NameViewHolder extends RecyclerView.ViewHolder {

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
