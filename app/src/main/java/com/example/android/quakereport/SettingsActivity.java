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

			Preference orderBy = findPreference(getString(R.string.order_by_key_settings));
			bindPreferenceToValue(orderBy);
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
			if (preference instanceof ListPreference) {
				ListPreference listPreference = (ListPreference) preference;
				int prefIndex = listPreference.findIndexOfValue(stringValue);
				if (prefIndex >= 0) {
					CharSequence[] labels = listPreference.getEntries();
					preference.setSummary(labels[prefIndex]);
				}
			} else {
				preference.setSummary(stringValue);
			}
			return true;
		}
	}
}
