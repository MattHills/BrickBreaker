package com.example.brickbreaker;

import android.graphics.Color;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Ball {
	
    int xPosition;
    int yPosition;
    
    boolean initialized;
    float radius = 5;
    float xVelocity;
    float yVelocity;
    float speed;
    
    int canvasHeight;
    int canvasWidth;
    
    public static final int BALL_COLOUR = Color.LTGRAY;

    public Ball() 
    {
    	initialized = false;
    	speed = 1;
    	xVelocity = -3;
    	yVelocity = -3;
    }
    
    public void setPosition(int x, int y)
    {
    	xPosition = x;
    	yPosition = y;
    }
    
    public float getSpeed()
    {
    	return speed;
    }
    
    public void setSpeed(float speed)
    {
    	this.speed = speed;
    }
    
    public void initialize(int canvasWidth, int canvasHeight)
    {
    	initialized = true;
    	this.canvasWidth = canvasWidth;
    	this.canvasHeight = canvasHeight;
    	setPosition(canvasWidth / 2, canvasHeight - 50);
    }
    
    public void updatePosition()
    {
    	xPosition += speed * xVelocity; 
    	yPosition += speed * yVelocity;
    	
    	if (xPosition >= (canvasWidth - radius))
    	{
    	    xPosition = (int)(canvasWidth - radius);
    	    xVelocity *= -1.0;
    	} 
    	else if (xPosition <= radius) 
    	{
    	    xPosition = (int)radius;
    	    xVelocity *= -1.0;
    	}
    	
    	if (yPosition >= (canvasHeight - radius))
    	{
    	    yPosition = (int)(canvasHeight - radius);
    	    yVelocity *= -1.0;
    	} 
    	else if (yPosition <= radius) 
    	{
    	    yPosition = (int)radius;
    	    yVelocity *= -1.0;
    	}
    }
}
