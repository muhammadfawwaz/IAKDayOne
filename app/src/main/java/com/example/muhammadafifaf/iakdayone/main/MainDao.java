package com.example.muhammadafifaf.iakdayone.main;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Muhammad Afif AF on 01/12/2017.
 */

public class MainDao implements Parcelable{
    private String title;
    private String imageUrl;
    private String description;
    private String imageBackground;
    private String releaseDate;

    public MainDao(String title, String imageUrl, String description, String imageBackground, String releaseDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.imageBackground = imageBackground;
        this.releaseDate = releaseDate;
    }

    protected MainDao(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        imageBackground = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MainDao> CREATOR = new Creator<MainDao>() {
        @Override
        public MainDao createFromParcel(Parcel in) {
            return new MainDao(in);
        }

        @Override
        public MainDao[] newArray(int size) {
            return new MainDao[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeString(imageBackground);
        parcel.writeString(releaseDate);
    }
}
