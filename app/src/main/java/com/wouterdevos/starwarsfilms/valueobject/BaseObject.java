package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

public class BaseObject {

    private long id;
    @SerializedName("url")
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
}
