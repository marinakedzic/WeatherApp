package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    String city;
    String API = "b2922f84f9e7b59666f8896ceb503194";
    Float lon;
    Float lat;
    String lang;
    String unit;

    TextView currentWindTxt, currentTimeTxt, currentDateTxt,
            currentRainTxt, currentTempTxt, currentLocationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTimeTxt = findViewById(R.id.timeCurrent);
        currentDateTxt = findViewById(R.id.dateCurrent);
        currentWindTxt = findViewById(R.id.windCurrent);
        currentRainTxt = findViewById(R.id.cloudsCurrent);
        currentTempTxt = findViewById(R.id.tempCurrent);
        currentLocationTxt = findViewById(R.id.Location);
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
        lon = sharedPreferences.getFloat("lon", 0);
        lat = sharedPreferences.getFloat("lat", 0);
        lang = sharedPreferences.getString("lang", "");
        unit = sharedPreferences.getString("unit", "");
        city = sharedPreferences.getString("city","");
        currentLocationTxt.setText(city);
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
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=" + unit + "&lang=" + lang + "&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);

                JSONObject currentWeather = jsonObj.getJSONObject("current");
                JSONArray hourlyWeather = jsonObj.getJSONArray("hourly");
                JSONObject weatherObj = currentWeather.getJSONArray("weather").getJSONObject(0);
                String weather = weatherObj.getString("main");
                Log.d("jandjaf", weather);
                if(weather.equals("Rain") || weather.equals("Drizzle")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.rain);
                }
                else if(weather.equals("Thunderstorm")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.thunder);
                }
                else if(weather.equals("Wind")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.wind);
                }
                else if(weather.equals("Snow")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.snow);
                }
                else if(weather.equals("Clouds") || weather.equals("Atmosphere")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.clouds);
                }
                else if(weather.equals("Clear")){
                    ImageView currentIcon = findViewById(R.id.mainIcon);
                    currentIcon.setImageResource(R.drawable.sun);
                }

                long time = currentWeather.getLong("dt");
                String updatedTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(time * 1000));
                String updatedDate = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(new Date(time * 1000));
                currentTimeTxt.setText(updatedTime);
                currentDateTxt.setText(updatedDate);
                double currentTempDouble = currentWeather.getDouble("temp");
                double currentWind = currentWeather.getDouble("wind_speed");
                if(unit.equals("metric")){
                int currentTemp = (int) Math.round(currentTempDouble);
                currentTempTxt.setText(currentTemp+ "°C");
                currentWindTxt.setText(currentWind+ "m/s");}
                else{
                    currentTempTxt.setText(currentTempDouble+ "F");
                    currentWindTxt.setText(currentWind+ "mph");}

                int currentRain = currentWeather.getInt("clouds");
                currentRainTxt.setText(currentRain + "%");

                ArrayList<Hourly> twelveHours = new ArrayList<>();
                for (int m = 0; m<12; m++){
                    JSONObject hour = hourlyWeather.getJSONObject(m);
                    String hourlyTempTxt;
                    String hourlyWindTxt;
                    if(!(hour.equals("rain"))) {
                        Hourly hourly = new Hourly();
                        long updatedAt = hour.getLong("dt");
                        String updatedAtText = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                        double hourlyTempDouble = currentWeather.getDouble("temp");
                        double hourlyWind = currentWeather.getDouble("wind_speed");
                        if(unit.equals("metric")){
                            int hourlyTemp = (int) Math.round(currentTempDouble);
                            hourlyTempTxt = hourlyTemp + "°C";
                            hourlyWindTxt = hourlyWind + "m/s";}
                        else{
                            hourlyTempTxt = hourlyTempDouble + "F";
                            hourlyWindTxt = hourlyWind+ "mph";}

                        JSONObject hourlyRainDescriptionObj = hour.getJSONArray("weather").getJSONObject(0);
                        String hourlyRainDescription = hourlyRainDescriptionObj.getString("description");
                        String weather1 = weatherObj.getString("main");
                        if(weather1.equals("Rain") || weather1.equals("Drizzle") || hourlyRainDescription.equals("light rain")){
                            hourly.setImage(R.drawable.rain);
                        }
                        else if(weather1.equals("Thunderstorm")){
                            hourly.setImage(R.drawable.thunder);
                        }
                        else if(weather1.equals("Wind")){
                            hourly.setImage(R.drawable.wind);
                        }
                        else if(weather1.equals("Snow")){
                            hourly.setImage(R.drawable.snow);
                        }
                        else if(weather1.equals("Clouds") || weather1.equals("Atmosphere")){
                           hourly.setImage(R.drawable.clouds);
                        }
                        else if(weather1.equals("Clear")){
                            hourly.setImage(R.drawable.sun);
                        }
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

public void goOnDaily(View view) {
    Intent intent = new Intent(this, DailyActivity.class);
    startActivity(intent);
}
public void settings(View view){
        Intent intent = new Intent(this, EditSettings.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}
