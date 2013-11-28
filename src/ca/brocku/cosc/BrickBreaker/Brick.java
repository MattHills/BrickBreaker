package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick {
	//position and size
	int xPosition;
	int yPosition;
	int brickWidth;
	int brickHeight;
	
    int colour;
    int hitsRequired;
    int hitsTaken;

    public Brick(int xPosition, int yPosition, int brickWidth, int brickHeight, int colour, int hitsRequired) {
	
    	this.xPosition = xPosition;
    	this.yPosition = yPosition;
    	this.brickWidth = brickWidth;
    	this.brickHeight = brickHeight;
		this.colour = colour;
		this.hitsRequired = hitsRequired;
		hitsTaken = 0;
    }
    
    public Rect getRect()
    {
    	return new Rect(xPosition, yPosition, yPosition + brickWidth, xPosition + brickHeight);
    }
}
