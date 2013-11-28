package ca.brocku.cosc.BrickBreaker;

/* Matt Hills
 * 4640512
 * mh09wq
 * Cosc 3V97
 * Assignment 2
 * 
 * App Preferences
 * 
 * Friday, November 8th, 2013
 * ContactAppPreferencesActivty.java
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

@SuppressLint("NewApi")
public class BrickBreakerPreferencesActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	getFragmentManager().beginTransaction()
		.replace(android.R.id.content, new BrickBreakerPreferences())
		.commit();
    }
}
