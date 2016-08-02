package com.wouterdevos.starwarsfilms.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wouterdevos.starwarsfilms.database.DatabaseHelper;
import com.wouterdevos.starwarsfilms.valueobject.ETag;
import com.wouterdevos.starwarsfilms.valueobject.ErrorResponse;
import com.wouterdevos.starwarsfilms.valueobject.Film;
import com.wouterdevos.starwarsfilms.valueobject.FilmsResponse;
import com.wouterdevos.starwarsfilms.valueobject.People;
import com.wouterdevos.starwarsfilms.valueobject.PeopleResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StarWarsApiController {

    private static final String BASE_URL = "http://swapi.co/api/";

    public static final String PATH_FILMS = "films/?format=json";
    public static final String PATH_PEOPLE = "people/?format=json";

    public static final String QUERY_FORMAT_JSON = "?format=json";

    private static final String HEADER_IF_NONE_MATCH = "If-None-Match";

    private static final int HTTP_REDIRECTION_304 = 304;

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
//        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
//        final ETag eTag = databaseHelper.readETag(BASE_URL + PATH_FILMS);

        Retrofit retrofit = getRetrofitInstance();
        StarWarsApiService apiService = retrofit.create(StarWarsApiService.class);
        Call<FilmsResponse> call = apiService.getFilms();
        call.enqueue(new Callback<FilmsResponse>() {
            @Override
            public void onResponse(Call<FilmsResponse> call, Response<FilmsResponse> response) {
                if (response.isSuccessful()) {
//                    Long id = eTag != null ? eTag.getId() : null;
//                    String url = call.request().url().toString();
//                    String value = response.headers().get(HEADER_IF_NONE_MATCH);
//                    databaseHelper.insertETag(id, url, value);
//                    insertETag(eTag, call, response, databaseHelper);
//                    databaseHelper.insertFilms(response.body());
                    EventBus.getDefault().post(response.body());
                } else if (response.code() != HTTP_REDIRECTION_304){
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
//        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        List<People> people = new ArrayList<>();
        getPeople(apiService, people, film, 0);
    }

    private static void getPeople(final StarWarsApiService apiService, final List<People> people,
                                 final Film film, final int index) {
        String peopleUrl = film.getCharacters().get(index);
//        final ETag eTag = databaseHelper.readETag(peopleUrl);
        Call<PeopleResponse> call = apiService.getPeople(peopleUrl + QUERY_FORMAT_JSON);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                if (response.isSuccessful()) {
//                    Long id = eTag != null ? eTag.getId() : null;
//                    String url = call.request().url().toString();
//                    String value = response.headers().get(HEADER_IF_NONE_MATCH);
//                    databaseHelper.insertETag(id, url, value);
//                    insertETag(eTag, call, response, databaseHelper);
//                    databaseHelper.insertPeople(film.getId(), response.body());
                }

                if (response.isSuccessful()) {
                    if ((index + 1) < film.getCharacters().size()) {
                        List<People> responsePeople = response.body().getPeople();
                        people.addAll(responsePeople);
                        getPeople(apiService, people, film, index + 1);
                    } else {
                        EventBus.getDefault().post(film.getCharacters());
                    }
                } else {
                    postRequestFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                postRequestFailed(UNKNOWN_STATUS_CODE);
            }
        });
    }

    private static void insertETag(ETag eTag, Call<?> call, Response<?> response, DatabaseHelper databaseHelper) {
        Long id = eTag != null ? eTag.getId() : null;
        String url = call.request().url().toString();
        String value = response.headers().get(HEADER_IF_NONE_MATCH);
        databaseHelper.insertETag(id, url, value);
    }

    private static void postRequestFailed(int statusCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(statusCode);
        EventBus.getDefault().post(errorResponse);
    }
}
