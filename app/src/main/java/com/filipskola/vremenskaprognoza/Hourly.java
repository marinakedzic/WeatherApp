package com.filipskola.vremenskaprognoza;



public class Hourly {
    private String time;
    private String temp;
    private String wind;
    private String description;
    private String rain;
    public Hourly(String time,  String temp, String wind, String rain, String description){
        this.time = time;
        this.temp = temp;
        this.wind = wind;
        this.description = description;
        this.rain = rain;
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

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }
}
