package ca.brocku.cosc.BrickBreaker;


import com.example.brickbreaker.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class BrickBreakerPreferences extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.preferences);
    }
}
