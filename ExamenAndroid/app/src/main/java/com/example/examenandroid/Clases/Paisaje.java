package com.example.examenandroid.Clases;

import com.google.gson.annotations.SerializedName;

public class Paisaje {

    @SerializedName("id")
    String id;
    String nombre;
    String fotito;
    String latitud;
    String longitud;

    public Paisaje() {
    }

    public Paisaje(String nombre, String fotito, String latitud, String longitud) {
        this.nombre = nombre;
        this.fotito = fotito;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotito() {
        return fotito;
    }

    public void setFotito(String fotito) {
        this.fotito = fotito;
    }
}
