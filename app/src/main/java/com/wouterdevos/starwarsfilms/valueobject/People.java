package com.wouterdevos.starwarsfilms.valueobject;

import com.google.gson.annotations.SerializedName;

public class People {

    private boolean isFinal = true;
    @SerializedName("name")
    private String name;

    public People () {

    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
