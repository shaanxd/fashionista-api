package com.fashionista.api.rest;

import com.fashionista.api.dtos.response.ImageResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ImgurService {
    @POST("image")
    @Multipart
    Call<ImageResponse> post(
            @Header("Authorization") String client,
            @Part MultipartBody.Part file
    );
}
