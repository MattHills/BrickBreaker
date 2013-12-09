package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * The class to represent a brick on the screen
 * 
 * @author Dan Lapp, Matt Hills
 */
public class Brick {

	//constants representing collision with left and right or top and bottom
    public static final int TOP_BOTTOM = 1;
    public static final int LEFT_RIGHT = 2;
    
    //score per brick hit constants
    public static final int SCORE_EASY = 10;
    public static final int SCORE_MED = 15;
    public static final int SCORE_HARD = 25;

    // position and size variables
    int xPosition;
    int yPosition;
    int brickWidth;
    int brickHeight;
    
    //this bricks rectangle object
    Rect rect;

    /**
     * Colour of the brick and number of hits required/taken
     * 
     * The colour array represents the colour of the brick based on how
     * many hits are required and how many have been taken.
     */
    int[] colour;
    int hitsRequired;
    int hitsTaken;

    /**
     * Creates a new Brick
     * 
     * @param xPosition x position of brick (top left corner)
     * @param yPosition y position of brick (top left corner)
     * @param brickWidth width of the brick
     * @param brickHeight height of the brick
     * @param colour colour array for brick colour based on hits taken
     * @see ca.brocku.cosc.BrickBreaker.Colour
     * @param hitsRequired
     */
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

    /**
     * Get the Rect object that represents this brick
     * @return this brick's Rect
     * @see android.graphics.Rect
     */
    public Rect getRect() {
    	return rect;
    }

    /**
     * Check for ball collision with this brick
     * @param boundingBox the collision box for the current ball instance
     * @return LEFT_RIGHT for side, TOP_BOTTOM for top/bottom collision,
     * 0 otherwise
     */
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
    
    /**
     * Adds behaviour when the ball collides with brick. Increments hitsTaken
     */
    private void onCollision()
    {
    	hitsTaken++;
    }
    
    /**
     * Gets the colour array for adding colours to the brick
     * @return colour constant array associated with this brick
     * @see ca.brocku.cosc.BrickBreaker.Colour
     */
    public int getColour()
    {
    	return colour[(hitsRequired - 1) - hitsTaken];
    }
}
