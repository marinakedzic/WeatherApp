package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.androdocs.httprequest.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String CITY = "dhaka,bd";
    String API = "8118ed6ee68db2debfaaa5a44c832918";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt,  hourlyTempTxt, hourlyTimeTxt, currentWindTxt, currentTimeTxt,
            currentRainTxt, currentTempTxt, hourlyRainTxt, hourlyRainDescriptionTxt, hourlyWindTxt, dailyRainDescriptionTxt,
            dailyTempTxt, dailyWindTxt, dailyTimeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);*/
        hourlyTimeTxt = findViewById(R.id.hourlyTime);
        hourlyTempTxt = findViewById(R.id.hourlyTemp);
        hourlyWindTxt = findViewById(R.id.hourlyWind);
        hourlyRainTxt = findViewById(R.id.hourlyRain);
        hourlyRainDescriptionTxt = findViewById(R.id.hourlyRainDescription);
        currentTimeTxt = findViewById(R.id.currentTime);
        currentWindTxt = findViewById(R.id.currentWind);
        currentRainTxt = findViewById(R.id.currentRain);
        currentTempTxt = findViewById(R.id.currentTemp);

        dailyRainDescriptionTxt = findViewById(R.id.dailyRainDescription);
        dailyTempTxt = findViewById(R.id.dailyTemp);
        dailyWindTxt = findViewById(R.id.dailyWind);
        dailyTimeTxt = findViewById(R.id.dailyTime);
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
                JSONObject hourlyWeather = jsonObj.getJSONArray("hourly").getJSONObject(0);
                JSONObject dailyWeather1 = jsonObj.getJSONArray("daily").getJSONObject(1);

                Long time = currentWeather.getLong("dt");
                String updatedtime = "Time " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(time * 1000));
                currentTimeTxt.setText(updatedtime);
                Double currentTempDouble = currentWeather.getDouble("temp");
                int currentTemp = (int) Math.round(currentTempDouble);
                currentTempTxt.setText(currentTemp+ "°C");
                String currentWind = String.valueOf(currentWeather.getDouble("wind_speed"));
                currentWindTxt.setText(currentWind+ "m/s");
                String currentRain = currentRainObj.getString("main");
                currentRainTxt.setText(currentRain);

                ArrayList<Hourly> twelveHours = new ArrayList<Hourly>();
                for (int i = 0; i<=12; i++){
                    JSONObject hourlyWeather = jsonObj.getJSONArray("hourly").getJSONObject(0);
                    Long updatedAt = hourlyWeather.getLong("dt");
                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                    Double hourlyTempDouble = hourlyWeather.getDouble("temp");
                    int hourlyTemp = (int) Math.round(hourlyTempDouble);
                    String hourlyTempTxt = hourlyTemp + "°C";
                    Double hourlyWind = hourlyWeather.getDouble("wind_speed");
                    String hourlyWindTxt = hourlyWind+ "m/s";
                    JSONObject hourlyRainPercent = hourlyWeather.getJSONObject("rain");
                    Double hourlyRainPercent1 = hourlyRainPercent.getDouble("1h");
                    Double percent = hourlyRainPercent1*100;
                    String hourlyRainTxt = percent + "%";
                    JSONObject hourlyRainDescriptionObj = hourlyWeather.getJSONArray("weather").getJSONObject(0);
                    String hourlyRainDescription = hourlyRainDescriptionObj.getString("description");
                    twelveHours.add(new Hourly(updatedAtText, hourlyTempTxt, hourlyWindTxt, hourlyRainTxt, hourlyRainDescription));

                }
                    HourlyAdapter hourlyAdapter = new HourlyAdapter(this, twelveHours);

                    ListView listViewHourly = findViewById(R.id.listview_flavor);

                    listViewHourly.setAdapter(hourlyAdapter);

                Long updatedAt = hourlyWeather.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                hourlyTimeTxt.setText(updatedAtText);

                Double hourlyTempDouble = hourlyWeather.getDouble("temp");
                int hourlyTemp = (int) Math.round(hourlyTempDouble);
                hourlyTempTxt.setText(hourlyTemp + "°C");
                Double hourlyWind = hourlyWeather.getDouble("wind_speed");
                hourlyWindTxt.setText(hourlyWind+ "m/s");
                JSONObject hourlyRainPercent = hourlyWeather.getJSONObject("rain");
                Double hourlyRainPercent1 = hourlyRainPercent.getDouble("1h");
                Double percent = hourlyRainPercent1*100;
                hourlyRainTxt.setText(percent + "%");
                JSONObject hourlyRainDescriptionObj = hourlyWeather.getJSONArray("weather").getJSONObject(0);
                String hourlyRainDescription = hourlyRainDescriptionObj.getString("description");
                hourlyRainDescriptionTxt.setText(hourlyRainDescription);




                ArrayList<Daily> sevenDays = new ArrayList<Daily>();
                for (int i = 1; i<=7; i++){
                    JSONObject dailyWeather = jsonObj.getJSONArray("daily").getJSONObject(i);
                    Long day = dailyWeather.getLong("dt");
                    String dayText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(day * 1000));
                    JSONObject dailyRainPercentObj = dailyWeather.getJSONObject("temp");
                    Double  dailyTempDouble = dailyRainPercentObj.getDouble("day");
                    int  dailyTemp = (int) Math.round(dailyTempDouble);
                    String dailyTempTxt = dailyTemp + "°C";
                    Double dailyWind = dailyWeather.getDouble("wind_speed");
                    String dailyWindTxt = dailyWind+ "m/s";
                    JSONObject dailyRainDescriptionObj = dailyWeather.getJSONArray("weather").getJSONObject(0);
                    String dailyRainDescription = dailyRainDescriptionObj.getString("description");
                    sevenDays.add(new Daily(dayText, dailyTempTxt, dailyWindTxt, dailyRainDescription));

                }
                    DailyAdapter dailyAdapter = new DailyAdapter(this, sevenDays);

                    ListView listViewDaily =  findViewById(R.id.listview_flavor);

                    listViewDaily.setAdapter(dailyAdapter);

                //wind speed description temp
                JSONObject dailyRainPercentObj = dailyWeather1.getJSONObject("temp");
                Double  dailyTempDouble = dailyRainPercentObj.getDouble("day");
                int  dailyTemp = (int) Math.round(dailyTempDouble);
                dailyTempTxt.setText(dailyTemp + "°C");
                Double dailyWind = dailyWeather1.getDouble("wind_speed");
                dailyWindTxt.setText(dailyWind+ "m/s");
                JSONObject dailyRainDescriptionObj = dailyWeather1.getJSONArray("weather").getJSONObject(0);
                String dailyRainDescription = dailyRainDescriptionObj.getString("description");
                dailyRainDescriptionTxt.setText(dailyRainDescription);

                Long day = dailyWeather1.getLong("dt");
                String dayText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(day * 1000));
                dailyTimeTxt.setText(dayText);
                /*findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);*/}
            catch (Exception e){}

        }
    }
}
