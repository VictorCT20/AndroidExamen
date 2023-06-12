package com.example.examenandroid.Clases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Publicacion {

    @SerializedName("id")
    String id;
    String foto;
    String descripcion;
    List<String> comentarios;

    public Publicacion() {
    }

    public Publicacion(String id, String foto, String descripcion, List<String> comentarios) {
        this.id = id;
        this.foto = foto;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}
