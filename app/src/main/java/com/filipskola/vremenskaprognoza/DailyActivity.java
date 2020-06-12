package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyActivity extends AppCompatActivity {
    String city;
    String API = "8118ed6ee68db2debfaaa5a44c832918";
    Float lon;
    Float lat;
    String lang;
    String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
        lon = sharedPreferences.getFloat("lon", 0);
        lat = sharedPreferences.getFloat("lat", 0);
        lang = sharedPreferences.getString("lang", "sr");
        unit = sharedPreferences.getString("unit", "metric");
        city = sharedPreferences.getString("city","Belgrade");
        new weatherTaskDaily().execute();
    }
    class weatherTaskDaily extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* *//* Showing the ProgressBar, Making the main design GONE *//*
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);*/
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=" + unit + "&lang=" + lang + "&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                ArrayList<Daily> sevenDays = new ArrayList<>();
                for (int i = 1; i <= 7; i++) {
                    Daily daily = new Daily();
                    JSONObject dailyWeather = jsonObj.getJSONArray("daily").getJSONObject(i);
                    String dailyTempTxt;
                    String dailyWindTxt;
                    Long day = dailyWeather.getLong("dt");
                    String dayText = new SimpleDateFormat("dd.MM", Locale.ENGLISH).format(new Date(day * 1000));
                    daily.setTime(dayText);
                    JSONObject dailyRainPercentObj = dailyWeather.getJSONObject("temp");
                    Double dailyTempDouble = dailyRainPercentObj.getDouble("day");
                    Double dailyWind = dailyWeather.getDouble("wind_speed");
                    if(unit.equals("metric")){
                        int dailyTemp = (int) Math.round(dailyTempDouble);
                        daily.setTemp(dailyTemp + "°C");
                        daily.setWind(dailyWind + "m/s");}
                    else{
                        daily.setTemp(dailyTempDouble + "F");
                        daily.setWind(dailyWind + "mph");}

                    JSONObject dailyRainDescriptionObj = dailyWeather.getJSONArray("weather").getJSONObject(0);
                    String dailyRainDescription = dailyRainDescriptionObj.getString("description");
                    daily.setDescription(dailyRainDescription);
                    String weather1 = dailyRainDescriptionObj.getString("main");
                    if(weather1.equals("Rain") || weather1.equals("Drizzle")){
                        daily.setImage(R.drawable.rain);
                    }
                    else if(weather1.equals("Thunderstorm")){
                        daily.setImage(R.drawable.thunder);
                    }
                    else if(weather1.equals("Wind")){
                        daily.setImage(R.drawable.wind);
                    }
                    else if(weather1.equals("Snow")){
                        daily.setImage(R.drawable.snow);
                    }
                    else if(weather1.equals("Clouds") || weather1.equals("Atmosphere")){
                        daily.setImage(R.drawable.clouds);
                    }
                    else if(weather1.equals("Clear")){
                        daily.setImage(R.drawable.sun);
                    }
                    sevenDays.add(daily);

                }
                DailyAdapter dailyAdapter = new DailyAdapter(sevenDays, getApplicationContext());

                ListView listViewDaily = findViewById(R.id.list);

                listViewDaily.setAdapter(dailyAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void onBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void settings(View view){
        Intent intent = new Intent(this, EditSettings.class);
        startActivity(intent);
    }
}