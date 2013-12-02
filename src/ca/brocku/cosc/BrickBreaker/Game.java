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

public class Game extends Activity implements SensorEventListener {

    // sensor variables
    private SensorManager sensorManager;
    private GamePanel gamePanel;
    private ArrayList<Score> globalScores;
    private ArrayList<Score> localScores;

    private static final int EASY_DIFFICULTY = 1;
    private static final int MEDIUM_DIFFICULTY = 2;
    private static final int HARD_DIFFICULTY = 3;

    /** Called when the activity is first created. */
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

	gamePanel = new GamePanel(this, size, globalScores, localScores, name,
		difficulty);
	setContentView(gamePanel);
    }

    protected void onPause() {
	super.onPause();

	/*
	 * Stop sensors
	 */
    }

    protected void onStart() {
	super.onStart();
	sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	// add listener. The listener will be HelloAndroid (this) class
	sensorManager.registerListener(this,
		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

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
