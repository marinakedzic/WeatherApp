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

public class DailyAdapter extends ArrayAdapter<Daily> {

    public DailyAdapter(Activity context, ArrayList<Daily> sevenDays) {
    super(context, 0, sevenDays);
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
        Daily currentDaily = getItem(position);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.version_name);
        timeTextView.setText(currentDaily.getTime());

        TextView tempTextView = (TextView) listItemView.findViewById(R.id.version_name);
        tempTextView.setText(currentDaily.getTemp());

        TextView windTextView = (TextView) listItemView.findViewById(R.id.version_name);
        windTextView.setText(currentDaily.getWind());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.version_name);
        descriptionTextView.setText(currentDaily.getDescription());

        return listItemView;


    }

}

/* Create an ArrayList of AndroidFlavor objects




    ArrayList<AndroidFlavor> androidFlavors = new ArrayList<AndroidFlavor>();


       androidFlavors.add(new AndroidFlavor("Donut", "1.6", R.drawable.donut));


               androidFlavors.add(new AndroidFlavor("Eclair", "2.0-2.1", R.drawable.eclair));


               androidFlavors.add(new AndroidFlavor("Froyo", "2.2-2.2.3", R.drawable.froyo));

// Create an {@link AndroidFlavorAdapter}, whose data source is a list of


               // {@link AndroidFlavor}s. The adapter knows how to create list item views for each item


               // in the list.


               AndroidFlavorAdapter flavorAdapter = new AndroidFlavorAdapter(this, androidFlavors);







               // Get a reference to the ListView, and attach the adapter to the listView.


               ListView listView = (ListView) findViewById(R.id.listview_flavor);


               listView.setAdapter(flavorAdapter); */

