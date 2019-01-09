package com.example.android.quakereport;
import android.support.v7.app.*;
import android.os.*;
import android.preference.*;

public class SettingsActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
	}
	
	public static class EarthquakePreferenceFragment extends PreferenceFragment {
		
	}
}
