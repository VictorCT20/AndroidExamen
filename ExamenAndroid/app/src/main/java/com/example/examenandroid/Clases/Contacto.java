package com.example.examenandroid.Clases;

import com.google.gson.annotations.SerializedName;

public class Contacto{

    @SerializedName("id")
    String id;
    String nombre;
    String number;
    String fotito;

    public Contacto() {
    }

    public Contacto(String nombre, String number, String fotito) {
        this.nombre = nombre;
        this.number = number;
        this.fotito = fotito;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFotito() {
        return fotito;
    }

    public void setFotito(String fotito) {
        this.fotito = fotito;
    }
}
