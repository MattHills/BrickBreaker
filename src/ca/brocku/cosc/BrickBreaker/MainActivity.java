package ca.brocku.cosc.BrickBreaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button quickPlay;
    Button leaderboards;

    TextView highScore;
    TextView username;

    JSONFunctions jsonFunctions;

    ArrayList<Score> scores;

    private final String filename = "BrickBreakerLocalLeaderboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	quickPlay = (Button) findViewById(R.id.QuickPlay);

	quickPlay.setOnClickListener(new View.OnClickListener() {

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

	username = (TextView) findViewById(R.id.Username);

	setName();

	highScore = (TextView) findViewById(R.id.HighScore);

	scores = readScores();

	displayHighScore();

	jsonFunctions = new JSONFunctions();
	jsonFunctions
		.execute("http://brockcoscbrickbreakerleaderboard.web44.net/");
	//
	// MySQLHelper db = new MySQLHelper(this);
	// db.getScores();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (requestCode == 1) {
	    if (resultCode == RESULT_OK) {
	    	setName();
	    }
	}

    }

    private void setName() {
	SharedPreferences prefs = PreferenceManager
		.getDefaultSharedPreferences(this);

	// Gets user's sort preferences
	if (prefs.getString("username", "").isEmpty()) {
	    username.setText("No username selected, please select user name in settings.");
	} else {
	    username.setText("Welcome " + prefs.getString("username", ""));
	}

    }

    private void displayHighScore() {
	if (scores != null && !scores.isEmpty()) {
	    ScoreComparator comparator = new ScoreComparator();
	    Collections.sort(scores, comparator);
	    Score hs = scores.get(0);
	    highScore.setText("High Score: " + hs.getScore());
	}
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Score> readScores() {
	ArrayList<Score> contacts = null;

	try {
	    File inputFile = null;
	    boolean exists = (new File(this.getFilesDir() + filename).exists());
	    // Create the file and directories if they do not exist
	    // This is for debugging a from a new instal of the app
	    // if (exists) {
	    // new File(this.getFilesDir() + filename).delete();
	    // exists = false;
	    // }
	    if (exists) {
		inputFile = new File(this.getFilesDir() + filename);
		FileInputStream fis = new FileInputStream(inputFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		contacts = (ArrayList<Score>) ois.readObject();
		ois.close();
		fis.close();
	    }
	    if (!exists) {
		scores = null;
		saveHighScore();
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return contacts;
    }

    // Saving scores to a file
    private void saveHighScore() {

	FileOutputStream os;

	try {
	    boolean exists = (new File(this.getFilesDir() + filename).exists());
	    // Create the file and directories if they do not exist
	    if (!exists) {
		new File(this.getFilesDir() + "").mkdirs();
		new File(this.getFilesDir() + filename);
	    }
	    // Saving the file
	    os = new FileOutputStream(this.getFilesDir() + filename, false);
	    ObjectOutputStream oos = new ObjectOutputStream(os);
	    oos.writeObject(scores);
	    oos.close();
	    os.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
