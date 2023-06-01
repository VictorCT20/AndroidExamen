package com.example.examenandroid.Service;

import com.example.examenandroid.Clases.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokemonService {
    @GET("Pokemon")
    Call<List<Pokemon>> getAllUser();

    @GET("Pokemon/{id}")
    Call<Pokemon> findUser(@Path("id") int id);

    @POST("Pokemon")
    Call<Pokemon> create(@Body Pokemon pokemon);

    @PUT("Pokemon/{id}")
    Call<Pokemon> update(@Path("id") int id, @Body Pokemon pokemon);

    @DELETE("Pokemon/{id}")
    Call<Void> delete(@Path("id") int id);
}
