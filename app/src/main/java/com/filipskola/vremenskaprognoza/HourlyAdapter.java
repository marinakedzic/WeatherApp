package com.filipskola.vremenskaprognoza;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HourlyAdapter extends ArrayAdapter<Hourly>{
    private ArrayList<Hourly> twelveHours;
    Context mContext;
    public HourlyAdapter(ArrayList<Hourly> twelveHours, Context context) {
        super(context,R.layout.hourly,twelveHours);
        this.twelveHours = twelveHours;
        this.mContext=context;

    }
    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hourly, parent, false);
        }
        Hourly currentHourly = getItem(position);

        TextView timeTextView = convertView.findViewById(R.id.timeHourly);
        timeTextView.setText(currentHourly.getTime());

        TextView tempTextView = convertView.findViewById(R.id.tempHourly);
        tempTextView.setText(currentHourly.getTemp());

        TextView windTextView = convertView.findViewById(R.id.windHourly);
        windTextView.setText(currentHourly.getWind());



        if(currentHourly.getRain() != null){
            TextView rainTextView = convertView.findViewById(R.id.rainHourly);
            rainTextView.setText(currentHourly.getRain());}
        else{
            TextView descriptionTextView = convertView.findViewById(R.id.rainHourly);
            descriptionTextView.setText(currentHourly.getDescription());
        }

        return convertView;


    }
}
