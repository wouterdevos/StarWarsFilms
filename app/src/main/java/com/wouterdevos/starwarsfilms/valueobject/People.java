package com.wouterdevos.starwarsfilms.valueobject;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class People extends BaseObject {

    @SerializedName("name")
    private String name;

    public People () {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
    }

    protected People(Parcel in) {
        super(in);
        this.name = in.readString();
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel source) {
            return new People(source);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };
}
