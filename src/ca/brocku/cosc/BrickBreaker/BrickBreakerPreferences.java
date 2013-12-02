package ca.brocku.cosc.BrickBreaker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class BrickBreakerPreferences extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.preferences);
    }
}
