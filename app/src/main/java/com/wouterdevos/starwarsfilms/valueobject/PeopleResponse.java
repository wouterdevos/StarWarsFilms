package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleResponse extends BaseResponse {

    @SerializedName("results")
    private List<People> people;

    public List<People> getPeople() {
        return people;
    }
}
