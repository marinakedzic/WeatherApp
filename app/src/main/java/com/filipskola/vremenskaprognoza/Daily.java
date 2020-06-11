package com.filipskola.vremenskaprognoza;

public class Daily {
    private String time;
    private int temp;
    private double wind;
    private String description;
    public Daily(String time,  int temp, double wind, String description){
        this.time = time;
        this.temp = temp;
        this.wind = wind;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
