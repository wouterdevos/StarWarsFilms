package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmsResponse {

    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private String previous;
    @SerializedName("count")
    private int count;
    @SerializedName("results")
    private List<Film> films;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }

    public List<Film> getFilms() {
        return films;
    }
}
