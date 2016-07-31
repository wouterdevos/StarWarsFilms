package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private String previous;
    @SerializedName("count")
    private int count;

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public int getCount() {
        return count;
    }
}
