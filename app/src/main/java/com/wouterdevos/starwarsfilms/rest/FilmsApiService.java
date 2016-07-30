package com.wouterdevos.starwarsfilms.rest;

import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmsApiService {

    @GET("films")
    Call<FilmsResponse> getFilms();
}
