package com.wouterdevos.starwarsfilms.rest;

import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;
import com.wouterdevos.starwarsfilms.valueobject.PeopleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface StarWarsApiService {

    @GET("films/?format=json")
    Call<FilmsResponse> getFilms();

    @GET
    Call<PeopleResponse> getPeople(@Url String url);
}
