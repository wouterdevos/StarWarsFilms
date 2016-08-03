package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmsResponse {

    @SerializedName("results")
    private List<Film> films;

    public List<Film> getFilms() {
        return films;
    }
}
