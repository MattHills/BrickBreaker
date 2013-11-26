package com.example.brickbreaker;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Game extends Activity implements SensorEventListener {	
	
	//sensor variables
	private SensorManager sensorManager;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    
	    setContentView(new GamePanel(this));
	    
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
	
		    //use sensor coords
		}
    }
}
