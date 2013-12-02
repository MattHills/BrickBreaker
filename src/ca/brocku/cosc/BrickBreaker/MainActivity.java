package ca.brocku.cosc.BrickBreaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button quickPlay;
    Button leaderboards;

    TextView highScore;
    TextView username;

    JSONFunctions jsonFunctions;

    ArrayList<Score> scores;
    ArrayList<Score> globalScores;

    Score hScore;

    Boolean onlineLeaderboard;

    BrickBreakerHelper helper;

    private final String filename = "BrickBreakerLocalLeaderboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	SharedPreferences prefs = PreferenceManager
		.getDefaultSharedPreferences(this);

	onlineLeaderboard = prefs.getBoolean("uploadToLeaderboard", false);

	helper = new BrickBreakerHelper();

	quickPlay = (Button) findViewById(R.id.QuickPlay);

	quickPlay.setOnClickListener(getQuickPlayOnClickListener());

	leaderboards = (Button) findViewById(R.id.Leaderboards);
	leaderboards.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this,
			Leaderboards.class);
		intent.putExtra("localScores", scores);
		intent.putExtra("globalScores", globalScores);
		intent.putExtra("highScore", hScore);
		startActivity(intent);
	    }
	});

	username = (TextView) findViewById(R.id.Username);

	setName();

	highScore = (TextView) findViewById(R.id.HighScore);

	scores = readScores();

	if (scores == null) {
	    scores = new ArrayList<Score>();
	}

	scores = helper.sortContacts(scores);

	displayHighScore();

	if (onlineLeaderboard) {
	    getOnlineLeaderboard();
	}

	//
	// MySQLHelper db = new MySQLHelper(this);
	// db.getScores();
    }

    private OnClickListener getQuickPlayOnClickListener() {
	return new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, Game.class);
		intent.putExtra("globalScores", globalScores);
		intent.putExtra("localScores", scores);

		startActivity(intent);
	    }
	};
    }

    protected void getOnlineLeaderboard() {
	jsonFunctions = new JSONFunctions();
	Handler handler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
		@SuppressWarnings("unchecked")
		ArrayList<Score> s = (ArrayList<Score>) msg.obj;
		globalScores = s;
		globalScores = helper.sortContacts(globalScores);
	    }
	};
	jsonFunctions.setHandler(handler);
	jsonFunctions
		.execute("http://brockcoscbrickbreakerleaderboard.web44.net/");
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
		SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(this);
		onlineLeaderboard = prefs.getBoolean("uploadToLeaderboard",
			false);
		if (onlineLeaderboard) {
		    getOnlineLeaderboard();
		    quickPlay.setOnClickListener(getQuickPlayOnClickListener());
		}
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
	    hScore = hs;
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
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return contacts;
    }

}
