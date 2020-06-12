package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
    String CITY = "dhaka,bd";
    String API = "8118ed6ee68db2debfaaa5a44c832918";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
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
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/onecall?lat=44.306938&lon=20.559999&units=metric&lang=sr&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                ArrayList<Daily> sevenDays = new ArrayList<>();
                for (int i = 1; i <= 7; i++) {
                    JSONObject dailyWeather = jsonObj.getJSONArray("daily").getJSONObject(i);
                    Long day = dailyWeather.getLong("dt");
                    String dayText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(day * 1000));
                    JSONObject dailyRainPercentObj = dailyWeather.getJSONObject("temp");
                    Double dailyTempDouble = dailyRainPercentObj.getDouble("day");
                    int dailyTemp = (int) Math.round(dailyTempDouble);
                    String dailyTempTxt = dailyTemp + "°C";
                    Double dailyWind = dailyWeather.getDouble("wind_speed");
                    String dailyWindTxt = dailyWind + "m/s";
                    JSONObject dailyRainDescriptionObj = dailyWeather.getJSONArray("weather").getJSONObject(0);
                    String dailyRainDescription = dailyRainDescriptionObj.getString("description");
                    sevenDays.add(new Daily(dayText, dailyTempTxt, dailyWindTxt, dailyRainDescription));

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
}