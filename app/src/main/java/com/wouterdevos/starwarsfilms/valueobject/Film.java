package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Film {

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

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<String> getCharacters() {
        return characters;
    }
}
