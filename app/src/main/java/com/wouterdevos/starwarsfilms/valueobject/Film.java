package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Film extends BaseObject {

    private static final String FILM_URL = "http://swapi.co/api/films/";

    @SerializedName("title")
    private String title;
    @SerializedName("opening_crawl")
    private String openingCrawl;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("characters")
    private List<String> characters = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public void setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }
}
