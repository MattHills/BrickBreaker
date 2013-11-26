package com.example.brickbreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Bar {

    Rect rect;
    int xPosition;
    int yPosition;
    
    int canvasHeight;
    int canvasWidth;
    float ballRadius;
    
    boolean initialized;
    
    public Bar()
    {
    	initialized = false;
    }

	public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
	}

    
    public void initialize(int canvasWidth, int canvasHeight, float ballRadius)
    {
    	this.canvasWidth = canvasWidth;
    	this.canvasHeight = canvasHeight;
    	this.ballRadius = ballRadius;
    	
    	rect = new Rect(canvasWidth / 2, (int)(canvasHeight - 50 + ballRadius), (canvasWidth / 2) + 100, canvasHeight - 80);
    }
    
    public void updatePosition()
    {
    	
    }
}
