/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
	
	private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
	
	private static final int EARTHQUAKE_LOADER_ID = 1;
	
	private EarthquakeInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
		
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeInfoAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
		
		LoaderManager eLoader = getLoaderManager();
		
		eLoader.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        // Set onClick listener to go to earthquake detail web page
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // find which earthquake was clicked
                Earthquake eClicked = mAdapter.getItem(i);

                // intent to open earthquake in browser
                Intent iWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(eClicked.getEarthquakeURL()));

                if (iWeb.resolveActivity(getPackageManager()) != null) {
                    startActivity(iWeb);
                } else {
                    Toast.makeText(EarthquakeActivity.this, R.string.toast_text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

	@Override
	public Loader<List<Earthquake>> onCreateLoader(int p1, Bundle p2)
	{
		// create loader
		return new EarthquakeLoader(this, USGS_REQUEST_URL);
	}

	@Override
	public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes)
	{
		// clear adapter
		mAdapter.clear();

		// if there is a valid list of earthquakes add them
		if (earthquakes != null && !earthquakes.isEmpty()) {
			mAdapter.addAll(earthquakes);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Earthquake>> p1)
	{
		// clear data
		mAdapter.clear();
	}
}
