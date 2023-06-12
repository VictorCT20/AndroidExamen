package com.example.examenandroid.Service;

import com.example.examenandroid.Clases.Contacto;
import com.google.gson.annotations.SerializedName;

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

    @POST("image")
    Call<ImageResponse> saveImage(@Body ImageToSave image);


    class ImageResponse {
        @SerializedName("url")
        private String url;

        public String getUrl(){
            return url;
        }
    }
    class ImageToSave {
        String base64Image;

        public ImageToSave(String base64Image){
            this.base64Image = base64Image;
        }
    }

}
