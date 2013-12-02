package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick {

    public static final int TOP_BOTTOM = 1;
    public static final int LEFT_RIGHT = 2;

    // position and size
    int xPosition;
    int yPosition;
    int brickWidth;
    int brickHeight;
    Rect rect;

    int[] colour;
    int hitsRequired;
    int hitsTaken;

    public Brick(int xPosition, int yPosition, int brickWidth, int brickHeight,
	    int[] colour, int hitsRequired) {

		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.brickWidth = brickWidth;
		this.brickHeight = brickHeight;
		this.colour = colour;
		this.hitsRequired = hitsRequired;
		hitsTaken = 0;
	
		rect = new Rect(0, 0, brickWidth, brickHeight);
		rect.offset(xPosition, yPosition);
    }

    public Rect getRect() {
    	return rect;
    }

    public int checkCollision(Rect boundingBox) {
		
    	Rect intersection = boundingBox;
    	
    	boolean intersectsBrick = intersection.intersect(rect);
    	
    	if(intersectsBrick)
	    	if((intersection.bottom - intersection.top) > (intersection.right - intersection.left))
	    	{
	    		onCollision();
	    		return Brick.LEFT_RIGHT;
	    	}
	    	else
	    	{
	    		onCollision();
	    		return Brick.TOP_BOTTOM;
	    	}

		return 0;
    }
    
    private void onCollision()
    {
    	hitsTaken++;
    }
    
    public int getColour()
    {
    	return colour[(hitsRequired - 1) - hitsTaken];
    }
}
