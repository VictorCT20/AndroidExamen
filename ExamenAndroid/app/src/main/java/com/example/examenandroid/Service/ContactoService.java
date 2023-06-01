package com.example.examenandroid.Service;

import com.example.examenandroid.Clases.Contacto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactoService {

    @GET("Contactos")
    Call<List<Contacto>> getAllUser();

    @GET("Contactos/{id}")
    Call<Contacto> findUser(@Path("id") int id);

    @POST("Contactos")
    Call<Contacto> create(@Body Contacto contacto);

    @PUT("Contactos/{id}")
    Call<Contacto> update(@Path("id") int id, @Body Contacto contacto);

    @DELETE("Contactos/{id}")
    Call<Void> delete(@Path("id") int id);


}
