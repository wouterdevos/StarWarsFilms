package com.wouterdevos.starwarsfilms.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;
import com.wouterdevos.starwarsfilms.valueobject.People;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StarWarsApiController {

    private static final String BASE_URL = "http://swapi.co/api/";

    public static final String QUERY_FORMAT_JSON = "?format=json";

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
        StarWarsApiService apiService = retrofit.create(StarWarsApiService.class);
        Call<FilmsResponse> call = apiService.getFilms();
        call.enqueue(new Callback<FilmsResponse>() {
            @Override
            public void onResponse(Call<FilmsResponse> call, Response<FilmsResponse> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
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

    public static void getPeople(Film film) {
        Retrofit retrofit = getRetrofitInstance();
        StarWarsApiService apiService = retrofit.create(StarWarsApiService.class);
        getPeople(apiService, film, 0);
    }

    private static void getPeople(final StarWarsApiService apiService, final Film film, final int index) {
        String peopleUrl = film.getCharacters().get(index);
        Call<People> call = apiService.getPeople(peopleUrl + QUERY_FORMAT_JSON);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                if (response.isSuccessful()) {
                    if ((index + 1) < film.getCharacters().size()) {
                        response.body().setFinal(false);
                        getPeople(apiService, film, index + 1);
                    }
                    EventBus.getDefault().post(response.body());
                } else {
                    postRequestFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {
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
