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

    @GET("contacto")
    Call<List<Contacto>> getAllUser();

    @GET("contacto/{id}")
    Call<Contacto> findUser(@Path("id") int id);

    @POST("contacto")
    Call<Contacto> create(@Body Contacto contacto);

    @PUT("contacto/{id}")
    Call<Contacto> update(@Path("id") int id, @Body Contacto contacto);

    @DELETE("contacto/{id}")
    Call<Void> delete(@Path("id") int id);


}
