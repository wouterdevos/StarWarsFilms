package com.wouterdevos.starwarsfilms.valueobject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Film implements Parcelable {

    @SerializedName("title")
    private String title;
    @SerializedName("opening_crawl")
    private String openingCrawl;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("characters")
    private List<String> characters = new ArrayList<>();

    public Film() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.openingCrawl);
        dest.writeString(this.releaseDate);
        dest.writeStringList(this.characters);
    }

    protected Film(Parcel in) {
        this.title = in.readString();
        this.openingCrawl = in.readString();
        this.releaseDate = in.readString();
        this.characters = in.createStringArrayList();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
