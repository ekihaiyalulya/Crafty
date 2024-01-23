package com.example.crafty;

import android.os.Parcel;
import android.os.Parcelable;

public class DataClass2 implements Parcelable {

    private String imageUrl, Caption1, Caption2, Caption3;


    public String getCaption3() {
        return Caption3;
    }

    public void setCaption3(String caption3) {
        Caption3 = caption3;
    }

    private String key;

    // Konstruktor default dan konstruktor parameter
    public DataClass2() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption1() {
        return Caption1;
    }

    public void setCaption1(String caption1) {
        Caption1 = caption1;
    }

    public String getCaption2() {
        return Caption2;
    }

    public void setCaption2(String caption2) {
        Caption2 = caption2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Implementasi Parcelable
    protected DataClass2(Parcel in) {
        imageUrl = in.readString();
        Caption1 = in.readString();
        Caption2 = in.readString();
        Caption3 = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(Caption1);
        dest.writeString(Caption2);
        dest.writeString(Caption3);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataClass2> CREATOR = new Creator<DataClass2>() {
        @Override
        public DataClass2 createFromParcel(Parcel in) {
            return new DataClass2(in);
        }

        @Override
        public DataClass2[] newArray(int size) {
            return new DataClass2[size];
        }
    };
}

