package com.example.android.quakereport;
import android.content.SharedPreferences;
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
	
	public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings_main);

			Preference minMagnitude = findPreference(getString(R.string.min_mag_key_settings));
			bindPreferenceToValue(minMagnitude);
		}

		private void bindPreferenceToValue(Preference minMagnitude) {
			minMagnitude.setOnPreferenceChangeListener(this);
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(minMagnitude.getContext());
			String preferenceString = preferences.getString(minMagnitude.getKey(), "");
			onPreferenceChange(minMagnitude, preferenceString);
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();
			preference.setSummary(stringValue);
			return true;
		}
	}
}
