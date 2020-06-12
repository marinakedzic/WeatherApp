package com.filipskola.vremenskaprognoza;

public class Daily {
    private String time;
    private String temp;
    private String wind;
    private String description;
    private int image;
    public Daily(){}
    public Daily(String time,  String temp, String wind, String description, int image){
        this.time = time;
        this.temp = temp;
        this.wind = wind;
        this.description = description;
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
