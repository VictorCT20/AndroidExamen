package com.example.examenandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.Clases.Publicacion;
import com.example.examenandroid.R;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter{

    private List<String> comentarios;

    private Context context;

    public ComentarioAdapter(List<String> comentarios, Context context) {
        this.comentarios = comentarios;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comentarios, parent, false);
        ComentarioAdapter.NameViewHolder viewHolder = new ComentarioAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String comentario = comentarios.get(position).toString();
        View view = holder.itemView;

        TextView txtComen = view.findViewById(R.id.tvComentarios);

        txtComen.setText(comentario);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
