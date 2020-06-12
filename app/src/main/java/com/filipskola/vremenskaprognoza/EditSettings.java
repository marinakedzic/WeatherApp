package com.filipskola.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class EditSettings extends AppCompatActivity {
    String API = "b2922f84f9e7b59666f8896ceb503194";
    String city;
    EditText location;
    String notification;
    String lang;
    String unit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        String packageName = getPackageName();
        sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
        lang = sharedPreferences.getString("lang", "");
        unit = sharedPreferences.getString("unit", "");
        city = sharedPreferences.getString("city","");
        notification = sharedPreferences.getString("notification", "");
        EditText location = findViewById(R.id.editLocation);
        location.setHint(city);
        ToggleButton unit1 = findViewById(R.id.unitsystem);
        ToggleButton lang1 = findViewById(R.id.lang);

        String unit = sharedPreferences.getString("unit", "");
        if (unit.equals("imperial")) {
            unit1.setChecked(true);
        } else {
            unit1.setChecked(false);
        }
        String lang = sharedPreferences.getString("lang", "");
        if (lang.equals("en")) {
            lang1.setChecked(true);
        } else {
            lang1.setChecked(false);
        }
    }

    public void lang(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            lang = "en";
        } else {
            lang = "sr";
        }
    }

    public void unit(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            unit = "imperial";
        } else {
            unit = "metric";
        }
    }

    public void start(View view) {
        editor = sharedPreferences.edit();
        location = findViewById(R.id.editLocation);
        city = location.getText().toString();
        editor.putString("city", city);
        editor.apply();

        new weatherTask().execute();
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
                    double latD = coor.getDouble("lat");
                    float lat = (float) latD;

                    editor = sharedPreferences.edit();
                    editor.putFloat("lon", lon);
                    editor.putFloat("lat", lat);
                    editor.putString("notification", notification);
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
