package com.example.examenandroid.Clases;

import android.media.Image;

public class Contacto {

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
