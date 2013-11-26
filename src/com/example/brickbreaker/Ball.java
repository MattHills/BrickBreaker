package com.example.brickbreaker;

import android.graphics.Color;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Ball {
	
    Position position;
    float radius = 5;
    public static final int BALL_COLOUR = Color.LTGRAY;

    public Ball() {
    	position = new Position();
    }
    
    public Position getPosition()
    {
    	return position;
    }
    
    public void setPosition(int x, int y)
    {
    	position.setX(x);
    	position.setY(y);
    }
    
    public void setPosition(Position p)
    {
    	position = p;
    }
}
