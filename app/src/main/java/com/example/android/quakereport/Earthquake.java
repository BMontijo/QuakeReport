package com.example.android.quakereport;

/**
 * Created by Brian on 1/2/2018.
 * Represents one earthquake event
 * Holds information
 * - magnitude
 * - location
 * - date
 */

public class Earthquake {
    // variable for magnitude
    private String earthquakeMagnitude;

    // variable for location
    private String earthquakeLocation;

    // variable for date
    private String earthquakeDate;

    // public constructor
    public Earthquake(String eMag, String eLoc, String eDate){
        // Set variables
        earthquakeMagnitude = eMag;
        earthquakeLocation = eLoc;
        earthquakeDate = eDate;
    }

    public String getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

    public String getEarthquakeLocation() {
        return earthquakeLocation;
    }

    public String getEarthquakeDate() {
        return earthquakeDate;
    }
}
