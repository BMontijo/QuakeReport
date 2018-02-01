package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Created by Brian on 1/2/2018.
 */

public class EarthquakeInfoAdapter extends ArrayAdapter<Earthquake> {
    // public constructor
    public EarthquakeInfoAdapter(Activity context, List<Earthquake> earthquake){
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get earthquake event at this position
        Earthquake currentEarthquake = getItem(position);

        // prepare current view
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // identify and populate views
        // format magnitude to one decimal place
        DecimalFormat magDec = new DecimalFormat("0.0");
        String magDis = magDec.format(currentEarthquake.getEarthquakeMagnitude());
        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude_view);
        magTextView.setText(magDis);

        // format location data
        String fullLocation = currentEarthquake.getEarthquakeLocation();
        String locationOffset = getContext().getString(R.string.near_offset);
        String primaryLocation;

        if (fullLocation.contains("of")) {
            String[] parts = fullLocation.split("(?<=of )");
            locationOffset = parts[0];
            primaryLocation = parts[1];
        } else {
            primaryLocation = fullLocation;
        }

        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        offsetTextView.setText(locationOffset);

        TextView locTextView = (TextView) listItemView.findViewById(R.id.primary_location_view);
        locTextView.setText(primaryLocation);

        // create date object to make human radable date
        Date dateObj = new Date(currentEarthquake.getEarthquakeTimeMs());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_view);
        String formattedDate = formatDate(dateObj);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_view);
        String formattedTime = formatTime(dateObj);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
