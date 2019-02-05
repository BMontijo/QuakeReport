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

import android.app.*;
import android.app.LoaderManager.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
	
	private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
	
	private static final int EARTHQUAKE_LOADER_ID = 1;
	
	private EarthquakeInfoAdapter mAdapter;
	
	private TextView mEmptyStateTextView;
	
	private ProgressBar mEarthquakeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
		
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
		
		// find empty state text view
		mEmptyStateTextView = (TextView) findViewById(R.id.emptyState);
		
		// find progress bar
		mEarthquakeProgressBar = (ProgressBar) findViewById(R.id.earthquakeProgressBar);

		// set empty state to list view
		earthquakeListView.setEmptyView(mEmptyStateTextView);
		
        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeInfoAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
		
		// connection manager
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni!=null && ni.isConnected()) {
			LoaderManager eLoader = getLoaderManager();
		
			eLoader.initLoader(EARTHQUAKE_LOADER_ID, null, this);
		} else {
			mEarthquakeProgressBar.setVisibility(View.GONE);
			mEmptyStateTextView.setText(getResources().getString(R.string.no_connection));
		}

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
		
		// set empty state text
		mEmptyStateTextView.setText(getResources().getString((R.string.empty_state)));
		
		// hide progress bar
		mEarthquakeProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onLoaderReset(Loader<List<Earthquake>> p1)
	{
		// clear data
		mAdapter.clear();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	int id = item.getItemId();
    	if (id == R.id.action_settings) {
    		Intent settingsIntent = new Intent(this, SettingsActivity.class);
    		startActivity(settingsIntent);
    		return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
