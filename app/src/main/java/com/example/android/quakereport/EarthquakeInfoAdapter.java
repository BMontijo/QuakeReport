package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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
        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude_view);
        magTextView.setText(currentEarthquake.getEarthquakeMagnitude());

        TextView locTextView = (TextView) listItemView.findViewById(R.id.location_view);
        locTextView.setText(currentEarthquake.getEarthquakeLocation());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_view);
        dateTextView.setText(currentEarthquake.getEarthquakeDate());

        return listItemView;
    }
}
