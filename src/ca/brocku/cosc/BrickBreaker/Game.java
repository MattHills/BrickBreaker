package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Window;

/**
 * Game activity that creates and initializes the drawing panel and
 * sets the drawing panel to this activity's content view. Also
 * handles the accelerometer sensor listeners, and relays that 
 * data to the game panel.
 * 
 * @author Dan Lapp, Matt Hills
 */
public class Game extends Activity implements SensorEventListener {

    // sensor variables
    private SensorManager sensorManager;
    private GamePanel gamePanel;
    private ArrayList<Score> globalScores;
    private ArrayList<Score> localScores;

    //Game constants for difficulty
    public static final int EASY_DIFFICULTY = 1;
    public static final int MEDIUM_DIFFICULTY = 2;
    public static final int HARD_DIFFICULTY = 3;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		globalScores = (ArrayList<Score>) getIntent().getSerializableExtra(
			"globalScores");
		localScores = (ArrayList<Score>) getIntent().getSerializableExtra(
			"localScores");
		
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		//get the size of the display
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
	
		String name;
		
		SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(this);
		name = prefs.getString("username", "NOT SET");
		
		String prefDiff = prefs.getString("difficulty", "Easy");
		int difficulty = EASY_DIFFICULTY;
	
		if (prefDiff.equalsIgnoreCase("Medium")) {
		    difficulty = MEDIUM_DIFFICULTY;
		} else if (prefDiff.equalsIgnoreCase("Hard")) {
		    difficulty = HARD_DIFFICULTY;
		}
	
		//initialize a game panel for drawing and set to content view
		gamePanel = new GamePanel(this, size, globalScores, localScores, name,
			difficulty);
		
		setContentView(gamePanel);
    }

    /* (non-Javadoc)
     * Stop the sensors when the app leaves focus
     * @see android.app.Activity#onPause()
     */
    protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
    }

    /* (non-Javadoc)
     * Start sensors when activity starts
     * @see android.app.Activity#onStart()
     */
    protected void onStart() {
		super.onStart();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	
		// add listener
		sensorManager.registerListener(this,
			sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
			SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	//not used
    }

    /* (non-Javadoc)
     * Update gamePanel with changed accelerometer values
     * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
     */
    public void onSensorChanged(SensorEvent event) {

		// check sensor type
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	
		    gamePanel.updateAccelerometer(-event.values[0]);
		    // mBallSpd.y = event.values[1];
		    // send values to the GamePanel
		    // gamePanel.updateAccelerometer(event.values[0], event.values[1],
		    // event.values[2]);
		}
    }
}
