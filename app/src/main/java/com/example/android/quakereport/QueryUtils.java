package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import android.text.*;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
	
	/**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
	
	// Create URL from string
	private static URL createUrl(String stringUrl) {
		URL url = null;
		try {
			url = new URL(stringUrl);
		} catch (MalformedURLException e) {
		    Log.e(QueryUtils.class.getName(), "Problem building the URL.", e);
		}
		return url;
	}
	
	// use url to make http request returning a string
	private static String makeHTTPRequest(URL url) throws IOException {
		String jsonResponse = "";
		
		// if url is null return early
		if (url == null) {
			return jsonResponse;
		}
		
		HttpURLConnection urlConnection = null;
		InputStream inputStream = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setReadTimeout(10000 /*milliseconds*/);
			urlConnection.setConnectTimeout(15000 /*milliseconds*/);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			
			// if connect (code 200) read input and parse
			if (urlConnection.getResponseCode() == 200) {
				inputStream = urlConnection.getInputStream();
				jsonResponse = readFromStream(inputStream);
			} else {
				Log.e(QueryUtils.class.getName(), "Error response code: " + urlConnection.getResponseCode());
			}
		} catch (IOException e) {
			Log.e(QueryUtils.class.getName(), "Problem retrieving the earthquake JSON results.", e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return jsonResponse;
	}
	
	// turn inputstream to string
	private static String readFromStream(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		if (inputStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = reader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = reader.readLine();
			}
		}
		return stringBuilder.toString();
	}
    
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Earthquake> extractEarthquakes(String earthquakeJSON) {

		// if JSON response is empty return early
		if (TextUtils.isEmpty(earthquakeJSON)) {
			return null;
		}
		
        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the JSON RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonRootObj = new JSONObject(earthquakeJSON);
            JSONArray jsonArray = jsonRootObj.optJSONArray("features");
            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                JSONObject prop = jsonObj.getJSONObject("properties");
                double mag = prop.optDouble("mag");
                String place = prop.optString("place");
                long timeInMs = prop.optLong("time");
                String eUrl = prop.optString("url");

                earthquakes.add(new Earthquake(mag, place, timeInMs,eUrl));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

	// query the USGS and return a list of earthquakes
	public static List<Earthquake> fetchEarthquakeData (String requestUrl) {
		URL url = createUrl(requestUrl);
		
		String jsonResponse = null;
		try {
			jsonResponse = makeHTTPRequest(url);
		} catch (IOException e) {
			Log.e(QueryUtils.class.getName(), "Problem making HTTP request.", e);
		}
		
		// extract info and make list of earthquakes
		List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
		
		// return earthquakes
		return earthquakes;
	}
}
