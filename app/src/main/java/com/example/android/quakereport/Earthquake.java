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
    private double earthquakeMagnitude;

    // variable for location
    private String earthquakeLocation;

    // variable for date
    private long earthquakeTimeMs;

    // public constructor
    public Earthquake(double eMag, String eLoc, long eTimeMs){
        // Set variables
        earthquakeMagnitude = eMag;
        earthquakeLocation = eLoc;
        earthquakeTimeMs = eTimeMs;
    }

    public double getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

    public String getEarthquakeLocation() {
        return earthquakeLocation;
    }

    public long getEarthquakeTimeMs() {
        return earthquakeTimeMs;
    }
}
