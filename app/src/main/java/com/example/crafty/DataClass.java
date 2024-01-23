package com.example.crafty;


public class DataClass {

    private String imageUrl, Caption1, Caption2, Caption3;


    public String getCaption3() {
        return Caption3;
    }

    public void setCaption3(String caption3) {
        Caption3 = caption3;
    }

    public DataClass(){

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

    public DataClass(String imageUrl, String caption1, String caption2, String caption3) {
        this.imageUrl = imageUrl;
        this.Caption1 = caption1;
        this.Caption2 = caption2;
        this.Caption3 = caption3;
    }
}
