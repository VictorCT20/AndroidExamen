package com.example.examenandroid.Service;

import com.example.examenandroid.Clases.Paisaje;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaisajeService {

    @GET("Paisaje")
    Call<List<Paisaje>> getAllUser();

    @GET("Paisaje/{id}")
    Call<Paisaje> findUser(@Path("id") int id);

    @POST("Paisaje")
    Call<Paisaje> create(@Body Paisaje paisaje);


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
