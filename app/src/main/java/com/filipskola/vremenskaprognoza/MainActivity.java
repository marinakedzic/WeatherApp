package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import com.androdocs.httprequest.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String CITY = "dhaka,bd";
    String API = "8118ed6ee68db2debfaaa5a44c832918";

    TextView currentWindTxt, currentTimeTxt,
            currentRainTxt, currentTempTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTimeTxt = findViewById(R.id.timeCurrent);
        currentWindTxt = findViewById(R.id.windCurrent);
        currentRainTxt = findViewById(R.id.rainCurrent);
        currentTempTxt = findViewById(R.id.tempCurrent);

        new weatherTask().execute();
    }


    class weatherTask extends AsyncTask<String, Void, String> {
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

                JSONObject currentWeather = jsonObj.getJSONObject("current");
                JSONObject currentRainObj = currentWeather.getJSONArray("weather").getJSONObject(0);
                JSONArray hourlyWeather = jsonObj.getJSONArray("hourly");
                long time = currentWeather.getLong("dt");
                String updatedTime = "Time " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(time * 1000));
                currentTimeTxt.setText(updatedTime);
                double currentTempDouble = currentWeather.getDouble("temp");
                int currentTemp = (int) Math.round(currentTempDouble);
                currentTempTxt.setText(currentTemp+ "°C");
                double currentWind = currentWeather.getDouble("wind_speed");
                currentWindTxt.setText(currentWind+ "m/s");
                String currentRain = currentRainObj.getString("main");
                currentRainTxt.setText(currentRain);

                ArrayList<Hourly> twelveHours = new ArrayList<>();
                for (int m = 0; m<12; m++){
                    JSONObject hour = hourlyWeather.getJSONObject(m);
                    if(!(hour.equals("rain"))) {
                        Hourly hourly = new Hourly();
                        long updatedAt = hour.getLong("dt");
                        String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                        double hourlyTempDouble = hour.getDouble("temp");
                        int hourlyTemp = (int) Math.round(hourlyTempDouble);
                        String hourlyTempTxt = hourlyTemp + "°C";
                        double hourlyWind = hour.getDouble("wind_speed");
                        String hourlyWindTxt = hourlyWind + "m/s";
                        JSONObject hourlyRainDescriptionObj = hour.getJSONArray("weather").getJSONObject(0);
                        String hourlyRainDescription = hourlyRainDescriptionObj.getString("description");
                        hourly.setTemp(hourlyTempTxt);
                        hourly.setTime(updatedAtText);
                        hourly.setWind(hourlyWindTxt);
                        hourly.setDescription(hourlyRainDescription);
                        twelveHours.add(hourly);
                    }

                    HourlyAdapter hourlyAdapter = new HourlyAdapter(twelveHours, getApplicationContext());

                    ListView listViewHourly = findViewById(R.id.list);

                    listViewHourly.setAdapter(hourlyAdapter);

                    }

                }catch (Exception e){}

        }


                /*findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);*/

}

public void goOnDaily(View view){
        Intent intent = new Intent(this, DailyActivity.class);
        startActivity(intent);
    }
}
