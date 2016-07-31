package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

public class People extends BaseObject {

    private static final String PEOPLE_URL = "http://swapi.co/api/people/";

    private long id;
    @SerializedName("name")
    private String name;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
