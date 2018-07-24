package com.example.android.quakereport;
import android.content.*;
import java.util.*;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> 
{
	private String mUrl;
	
	public EarthquakeLoader(Context context, String url){
		super(context);
		mUrl = url;
	}

	@Override
	protected void onStartLoading()
	{
		forceLoad();
	}

	@Override
	public List<Earthquake> loadInBackground()
	{
		if (mUrl == null) {
			return null;
		}
		
		List<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);
		return result;
	}
}
