package com.fashionista.api.services;

import com.fashionista.api.dtos.response.ImageErrorResponse;
import com.fashionista.api.dtos.response.ImageResponse;
import com.fashionista.api.exceptions.GenericException;
import com.fashionista.api.rest.ImgurService;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
    private ImgurService service;

    private String CLIENT_ID;

    public FileStorageService(
            @Value("${imgur.base.url}") String BASE_URL,
            @Value("${imgur.client.id}") String CLIENT_ID
    ) {
        this. CLIENT_ID = CLIENT_ID;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ImgurService.class);
    }

    String store(MultipartFile file) {
        try {
            Call<ImageResponse> call = service.post(
                    "Client-ID "+ CLIENT_ID,
                    MultipartBody.Part.createFormData(
                            "image",
                            file.getName(),
                            RequestBody.create(
                                    MediaType.parse("image/*"),
                                    file.getBytes()
                            )
                    ));

            Response<ImageResponse> response = call.execute();

            if (response.errorBody() != null || response.body() == null) {
                throw new Exception("Image upload request failed. Please try again.");
            }
            return response.body().getData().getLink();
        } catch (Exception e) {
            throw new GenericException("Error occurred while uploading images to bucket.", HttpStatus.BAD_REQUEST);
        }
    }

    List<String> storeMultiple(MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();

        for (MultipartFile file : files) {
            filenames.add(store(file));
        }

        return filenames;
    }
}
