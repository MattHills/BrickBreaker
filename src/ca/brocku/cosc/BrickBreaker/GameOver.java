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

/**
 * GameOver activity which is started when the user runs out of lives.
 * The user is presented with their score and a list of their high
 * scores on the content view
 * @author Matt Hills
 */
public class GameOver extends Activity {

	//layout widgets
    private ListView topScores;
    private Button mainMenu;
    private Button newGame;
    
    //list of high scores
    private ArrayList<Score> globalScores;
    private ArrayList<Score> localScores;
    
    //score adapter for display, helper for sorting scores
    private CustomListAdapter adapter;
    private BrickBreakerHelper helper;
    
    //user score
    private Score score;
    
    //variables for online score upload/download to server
    private JSONFunctions jsonFunctions;
    private Boolean onlineLeaderboard;
    
    //local score filepath
    private final String filename = "BrickBreakerLocalLeaderboard";


    /* (non-Javadoc)
     * Sets up the content view to display scores and populates the
     * list view with scores
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
	
		//get shared preferences
		SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(this);
	
		//does the user want to upload to online leaderboard
		onlineLeaderboard = prefs.getBoolean("uploadToLeaderboard", false);
	
		//get score extras from the intent
		this.globalScores = (ArrayList<Score>) getIntent()
			.getSerializableExtra("globalScores");
		this.localScores = (ArrayList<Score>) getIntent().getSerializableExtra(
			"localScores");
		this.score = (Score) getIntent().getSerializableExtra("score");
	
		//initialize score sorting object
		helper = new BrickBreakerHelper();
	
		//add new high score
		if (globalScores != null) {
		    globalScores.add(score);
		    globalScores = helper.sortScores(globalScores);
		}
		if (localScores != null) {
		    localScores.add(score);
		    localScores = helper.sortScores(localScores);
		}
	
		addScoreToLeaderboard();
		
		/* Set up layout for activity */
		//get layout widgets
		topScores = (ListView) findViewById(R.id.listview_top_scores);
		mainMenu = (Button) findViewById(R.id.menu_button);
		newGame = (Button) findViewById(R.id.new_game);
	
		//assign list view adapter, populate with scores
		if (onlineLeaderboard) {
		    adapter = new CustomListAdapter(this, R.layout.listitems,
			    globalScores, score);
		} else {
		    adapter = new CustomListAdapter(this, R.layout.listitems,
			    localScores, score);
		}
	
		topScores.setAdapter(adapter);
	
		/**
		 * Main Menu button on click listener
		 */
		mainMenu.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
	
			Intent intent = new Intent(GameOver.this, MainActivity.class);
			startActivity(intent);
		    }
		});
	
		/**
		 * New game button on click listener
		 */
		newGame.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
	
			Intent intent = new Intent(GameOver.this, Game.class);
			intent.putExtra("globalScores", globalScores);
			intent.putExtra("localScores", localScores);
			startActivity(intent);
		    }
		});
	
		/* onCreate */
    }

    
    /**
     * Upload score to online leaderboard if the user has the option selected
     */
    protected void addScoreToLeaderboard() {

		if (onlineLeaderboard) {
		    jsonFunctions = new JSONFunctions();
		    jsonFunctions.setUploadScore(score);
		    
		    Handler handler = new Handler() {
			    @Override
				public void handleMessage(Message msg) {
				    @SuppressWarnings("unchecked")
				    ArrayList<Score> s = (ArrayList<Score>) msg.obj;
				    globalScores = helper.sortScores(s);
			    }
		    };
		    
		    jsonFunctions.setHandler(handler);
		    jsonFunctions
			    .execute("http://brockcoscbrickbreakerleaderboard.web44.net/");
		}
		saveHighScore();
    }

    /**
     * Saves the score to a local file on the device
     */
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
