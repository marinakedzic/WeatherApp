package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
                JSONObject dailyWeather = jsonObj.getJSONArray("daily").getJSONObject(1);

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
                for (){
                    JSONObject dailyWeather = jsonObj.getJSONArray("daily").getJSONObject(1);
                }

                sevenDays.add(new Daily("Donut", "1.6", R.drawable.donut));


                sevenDays.add(new Daily("Eclair", "2.0-2.1", R.drawable.eclair));


                sevenDays.add(new Daily("Froyo", "2.2-2.2.3", R.drawable.froyo));
                //wind speed description temp
                JSONObject dailyRainPercentObj = dailyWeather.getJSONObject("temp");
                Double  dailyTempDouble = dailyRainPercentObj.getDouble("day");
                int  dailyTemp = (int) Math.round(dailyTempDouble);
                dailyTempTxt.setText(dailyTemp + "°C");
                Double dailyWind = dailyWeather.getDouble("wind_speed");
                dailyWindTxt.setText(dailyWind+ "m/s");
                JSONObject dailyRainDescriptionObj = dailyWeather.getJSONArray("weather").getJSONObject(0);
                String dailyRainDescription = dailyRainDescriptionObj.getString("description");
                dailyRainDescriptionTxt.setText(dailyRainDescription);

                Long day = dailyWeather.getLong("dt");
                String dayText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(day * 1000));
                dailyTimeTxt.setText(dayText);
                /* Populating extracted data into our views */
                /*addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
                temp_minTxt.setText(tempMin);
                temp_maxTxt.setText(tempMax);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                windTxt.setText(windSpeed);
                pressureTxt.setText(pressure);
                humidityTxt.setText(humidity);*/

                /* Views populated, Hiding the loader, Showing the main design */
                /*findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);*/}
            catch (Exception e){}

        }
    }
}
