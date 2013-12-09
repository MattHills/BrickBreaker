package ca.brocku.cosc.BrickBreaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Preference activity that brings up the preference fragment.
 * Allows the user to set shared preferences for the app
 * 
 * @author Matt Hills
 */
@SuppressLint("NewApi")
public class BrickBreakerPreferencesActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new BrickBreakerPreferences())
			.commit();
    }

    @Override
    public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		super.onBackPressed();
    }
}
