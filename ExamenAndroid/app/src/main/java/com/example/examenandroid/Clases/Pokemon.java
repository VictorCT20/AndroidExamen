package com.example.examenandroid.Clases;

import com.google.gson.annotations.SerializedName;

public class Pokemon {
    @SerializedName("id")
    String id;
    String pokedex;
    String nombre;
    String tipo;
    String imagen;

    public Pokemon() {
    }

    public String getPokedex() {
        return pokedex;
    }

    public void setPokedex(String pokedex) {
        this.pokedex = pokedex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
