package com.example.brickbreaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button quickPlay;
    Button login;
    Button leaderboards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	SharedPreferences prefs = PreferenceManager
		.getDefaultSharedPreferences(this);

	quickPlay = (Button) findViewById(R.id.QuickPlay);

	quickPlay.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, Game.class);
		startActivity(intent);
	    }
	});

	login = (Button) findViewById(R.id.Login);
	login.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, Game.class);
		startActivity(intent);
	    }
	});

	leaderboards = (Button) findViewById(R.id.Leaderboards);
	leaderboards.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, Game.class);
		startActivity(intent);
	    }
	});

    }

    // Add custom menu buttons for the phones built in menu button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    // Onclick for menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
	boolean ret = false;
	Intent intent;
	// Add new contact was selected, start the activity
	switch (menuItem.getItemId()) {

	case R.id.menuSettings:
	    intent = new Intent(this, BrickBreakerPreferencesActivity.class);
	    // expect a settings for retuns
	    startActivityForResult(intent, 1);
	    ret = true;
	    break;
	}
	return ret;
    }
}
