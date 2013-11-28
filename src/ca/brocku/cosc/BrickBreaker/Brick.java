package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick 
{
	
	public static final int SIDE_TOP = 1;
	public static final int SIDE_LEFT = 2;
	public static final int SIDE_RIGHT = 3;
	public static final int SIDE_BOTTOM = 4;
	
	//position and size
	int xPosition;
	int yPosition;
	int brickWidth;
	int brickHeight;
	
    int colour;
    int hitsRequired;
    int hitsTaken;

    public Brick(int xPosition, int yPosition, int brickWidth, int brickHeight, int colour, int hitsRequired) 
    {
	
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
    
    public int checkCollision(int ballPositionX, int ballPositionY, float ballRadius)
    {
    	/* top and bottom collision */
    	
    	//if the ball is within the width of the brick
    	if(ballPositionX >= xPosition && ballPositionX <= xPosition + brickWidth)
    	{
    		//collision with top
    		if(ballPositionY >= yPosition)
    			return Brick.SIDE_TOP;
    		
    		//collision with bottom
    		if(ballPositionY <= yPosition + brickHeight)
    			return Brick.SIDE_BOTTOM;
    	}
    	
    	//if the ball is within the height of the brick
    	if(ballPositionY >= yPosition && ballPositionY <= yPosition + brickHeight)
    	{
    		//collision with left
    		if(ballPositionX >= xPosition)
    			return Brick.SIDE_LEFT;
    		
    		//collision with right
    		if(ballPositionY <= xPosition + brickWidth)
    			return Brick.SIDE_RIGHT;
    	}
    	
    	return 0;
    }
}
