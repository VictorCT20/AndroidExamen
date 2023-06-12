package com.example.examenandroid.Service;

import com.example.examenandroid.Clases.Publicacion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PublicacionService {

    @GET("Publicacion")
    Call<List<Publicacion>> getAllUser();

    @GET("Publicacion/{id}")
    Call<Publicacion> findUser(@Path("id") int id);

    @POST("Publicacion")
    Call<Publicacion> create(@Body Publicacion publicacion);

    @PUT("Publicacion/{id}")
    Call<Publicacion> update(@Path("id") int id, @Body Publicacion publicacion);

    @DELETE("Publicacion/{id}")
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
