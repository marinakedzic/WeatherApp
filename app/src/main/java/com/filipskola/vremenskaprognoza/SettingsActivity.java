package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    String API = "b2922f84f9e7b59666f8896ceb503194";
    String city;
    EditText location;
    String lang;
    String unit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean isFirst = true;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            String packageName = getPackageName();
            sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings);
            Boolean isFirstRun = sharedPreferences.getBoolean("isFirst",true);
            if (!isFirstRun) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
    }

    public void start(View view) {
            editor = sharedPreferences.edit();
            location = findViewById(R.id.editLocation);
            city = location.getText().toString();
            editor.putString("city", city);
            editor.putBoolean("isFirst", isFirst);
            editor.apply();

            new weatherTask().execute();
        }

    public void lang(View view) {
            boolean on = ((ToggleButton) view).isChecked();
            if(on){
                lang = "en";
            }
            else{
                lang = "sr";
            }
    }

    public void unit(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if(on){
            unit = "imperial";
        }
        else{
            unit = "metric";
        }
    }

    class weatherTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... args) {
                String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=" + API);
                return response;
            }

            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject coor = jsonObj.getJSONObject("coord");
                    double lonD = coor.getDouble("lon");
                    float lon = (float) lonD;
                    double latD  = coor.getDouble("lat");
                    float lat = (float) latD;

                    editor = sharedPreferences.edit();
                    editor.putFloat("lon", lon);
                    editor.putFloat("lat", lat);
                    editor.putBoolean("isFirst", false);
                    editor.putString("unit", unit);
                    editor.putString("lang", lang);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                } catch (JSONException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),"This location is currently unavailable", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 70);
                    toast.show();

                }



            }
        }
}
