package com.filipskola.vremenskaprognoza;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DailyAdapter extends ArrayAdapter<Daily> {
    private ArrayList<Daily> sevenDays;
    Context mContext;
    public DailyAdapter(ArrayList<Daily> sevenDays, Context context) {
    super(context, R.layout.hourly, sevenDays);
        this.sevenDays = sevenDays;
        this.mContext=context;
    }
    private static final String LOG_TAG = ArrayAdapter.class.getSimpleName();
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daily, parent, false);
    }
        Daily currentDaily = getItem(position);

        TextView timeTextView = convertView.findViewById(R.id.timeDaily);
        timeTextView.setText(currentDaily.getTime());

        TextView tempTextView = convertView.findViewById(R.id.tempDaily);
        tempTextView.setText(currentDaily.getTemp());

        TextView windTextView = convertView.findViewById(R.id.windDaily);
        windTextView.setText(currentDaily.getWind());

        TextView descriptionTextView = convertView.findViewById(R.id.rainDescription);
        descriptionTextView.setText(currentDaily.getDescription());

        ImageView image = convertView.findViewById(R.id.dailyIcon);
        image.setImageResource(currentDaily.getImage());

        return convertView;


    }


}