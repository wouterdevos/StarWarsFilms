package com.wouterdevos.starwarsfilms.rest;

import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;
import com.wouterdevos.starwarsfilms.valueobject.PeopleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface StarWarsApiService {

    @GET("films/?format=json")
    Call<FilmsResponse> getFilms(@Header("If-None-Match") String eTag);

    @GET("?format=json")
    Call<PeopleResponse> getPeople(@Header("If-None-Match") String eTag, @Url String url);
}
