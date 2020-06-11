package com.filipskola.vremenskaprognoza;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HourlyAdapter extends ArrayAdapter<Hourly>{
    TextView rainTextView;
    public HourlyAdapter(Activity context, ArrayList<Hourly> twelveHours) {
        super(context, 0, twelveHours);
    }
    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);

        View listItemView = convertView;


        if(listItemView == null) {


            listItemView = LayoutInflater.from(getContext()).inflate(


                    R.layout.kacinLayout, parent, false);
        }
        Hourly currentDaily = getItem(position);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.version_name);
        timeTextView.setText(currentDaily.getTime());

        TextView tempTextView = (TextView) listItemView.findViewById(R.id.version_name);
        tempTextView.setText(currentDaily.getTemp());

        TextView windTextView = (TextView) listItemView.findViewById(R.id.version_name);
        windTextView.setText(currentDaily.getWind());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.version_name);
        descriptionTextView.setText(currentDaily.getDescription());

        if(currentDaily.getRain() != null){
            rainTextView = (TextView) listItemView.findViewById(R.id.version_name);
        rainTextView.setText(currentDaily.getRain());}
        else{
            rainTextView.setVisibility(View.GONE);
        }
        return listItemView;


    }
}
