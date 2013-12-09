package ca.brocku.cosc.BrickBreaker;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Creates a new PreferenceFragment. Adds shared preferences for
 * the app. Specifies settings like difficulty, upload scores
 * to online database, and username
 * 
 * @author Matt Hills
 */
public class BrickBreakerPreferences extends PreferenceFragment {
	
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
    }
}
