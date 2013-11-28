package ca.brocku.cosc.BrickBreaker;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

public class Game extends Activity implements SensorEventListener {	
	
	//sensor variables
	private SensorManager sensorManager;
	private GamePanel gamePanel;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    
	    //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    gamePanel = new GamePanel(this);
	    setContentView(gamePanel);
	    
	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    
	    //add listener. The listener will be HelloAndroid (this) class
	    sensorManager.registerListener(this,
	    		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	    		SensorManager.SENSOR_DELAY_NORMAL);
	
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

		// check sensor type
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			
			/**
			 * Remove 1 or 2 axis that are not needed, needs to be
			 * implemented with a real device
			 */
	
			//send values to the GamePanel
		    gamePanel.updateAccelerometer(event.values[0], 
							    		event.values[1], 
							    		event.values[2]);
		}
    }
}
