package ca.brocku.cosc.BrickBreaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class GameOver extends Activity {

    private ListView topScores;
    private Button mainMenu;
    private Button newGame;
    private ArrayList<Score> globalScores;
    private ArrayList<Score> localScores;
    private CustomListAdapter adapter;
    private BrickBreakerHelper helper;
    private Score score;
    private JSONFunctions jsonFunctions;
    private Boolean onlineLeaderboard;
    private final String filename = "BrickBreakerLocalLeaderboard";

    /** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.game_over);

	SharedPreferences prefs = PreferenceManager
		.getDefaultSharedPreferences(this);

	onlineLeaderboard = prefs.getBoolean("uploadToLeaderboard", false);

	this.globalScores = (ArrayList<Score>) getIntent()
		.getSerializableExtra("globalScores");
	this.localScores = (ArrayList<Score>) getIntent().getSerializableExtra(
		"localScores");
	this.score = (Score) getIntent().getSerializableExtra("score");

	helper = new BrickBreakerHelper();

	if (globalScores != null) {
	    globalScores.add(score);
	    globalScores = helper.sortContacts(globalScores);
	}
	if (localScores != null) {
	    localScores.add(score);
	    localScores = helper.sortContacts(localScores);
	}

	addScoreToLeaderboard();

	topScores = (ListView) findViewById(R.id.listview_top_scores);
	mainMenu = (Button) findViewById(R.id.menu_button);
	newGame = (Button) findViewById(R.id.new_game);

	if (onlineLeaderboard) {
	    adapter = new CustomListAdapter(this, R.layout.listitems,
		    globalScores);
	} else {
	    adapter = new CustomListAdapter(this, R.layout.listitems,
		    localScores);
	}

	topScores.setAdapter(adapter);

	mainMenu.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent(GameOver.this, MainActivity.class);
		startActivity(intent);
	    }
	});

	newGame.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent(GameOver.this, Game.class);
		startActivity(intent);
	    }
	});

	/* onCreate */
    }

    protected void addScoreToLeaderboard() {

	if (onlineLeaderboard) {
	    jsonFunctions = new JSONFunctions();
	    jsonFunctions.setUploadScore(score);
	    Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
		    @SuppressWarnings("unchecked")
		    ArrayList<Score> s = (ArrayList<Score>) msg.obj;
		    globalScores = s;
		    helper.sortContacts(globalScores);
		}
	    };
	    jsonFunctions.setHandler(handler);
	    jsonFunctions
		    .execute("http://brockcoscbrickbreakerleaderboard.web44.net/");
	}
	saveHighScore();

    }

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
	    oos.writeObject(localScores);
	    oos.close();
	    os.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void onStart() {
	super.onStart();

	// populate ListView topScores
    }
}
