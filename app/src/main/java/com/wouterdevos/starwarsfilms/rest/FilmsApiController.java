package com.wouterdevos.starwarsfilms.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmsApiController {

    private static final String BASE_URL = "http://swapi.co/api/";

    private static final int UNKNOWN_STATUS_CODE = -1;

    private static Retrofit sRetrofit;

    private static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            Gson gson = new GsonBuilder().create();
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return sRetrofit;
    }

    public static void getFilms() {
        Retrofit retrofit = getRetrofitInstance();
        FilmsApiService apiService = retrofit.create(FilmsApiService.class);
        Call<FilmsResponse> call = apiService.getFilms();
        call.enqueue(new Callback<FilmsResponse>() {
            @Override
            public void onResponse(Call<FilmsResponse> call, Response<FilmsResponse> response) {
                FilmsResponse filmsResponse = response.body();
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(filmsResponse);
                } else {
                    postRequestFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<FilmsResponse> call, Throwable t) {
                postRequestFailed(UNKNOWN_STATUS_CODE);
            }
        });
    }

    private static void postRequestFailed(int statusCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(statusCode);
        EventBus.getDefault().post(errorResponse);
    }
}
