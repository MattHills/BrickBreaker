package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * The class representing the sliding bar along the bottom of
 * the screen
 * 
 * @author Dan Lapp, Matt Hills
 *
 */
public class Bar {

	// position and size variables
    float xPosition;
    float yPosition;
    int barHeight;
    int barWidth;
    int barCenter;

    //device screen size
    int panelHeight;
    int panelWidth;

    //the rectangle used to draw the bar on screen
    private Rect rect;
    
    //is the bar ready to be drawn on screen or not
    boolean initialized;

    /**
     * Constructor to create a new bar. Needs to be initialized
     * by the GamePanel before use
     */
    public Bar() {
    	initialized = false;
    }

    /**
     * Set the position of the bar's Rect. This will update the position
     * of the bar on next screen redraw
     * 
     * @param x new x position
     * @param y new y position
     */
    public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
    }

    /**
     * Initialize the bar's values to their initial state and position
     * and sets bar width based on difficulty selected
     * 
     * @param panelWidth width of the device's screen
     * @param panelHeight height of the device's screen
     * @param difficulty the current game's difficulty level
     */
    public void initialize(int panelWidth, int panelHeight, int difficulty) {
    	
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
	
		if (difficulty == Game.EASY_DIFFICULTY) {
		    barWidth = panelWidth / 3;
		} else if (difficulty == Game.MEDIUM_DIFFICULTY) {
		    barWidth = panelWidth / 5;
		} else {
		    barWidth = panelWidth / 7;
		}
		barHeight = panelHeight / 40;
		barCenter = barWidth / 2;
	
		rect = new Rect((int) xPosition - barWidth / 2,
			(int) (panelHeight - 100 - barHeight),
			(int) (xPosition + barWidth / 2), (int) (panelHeight - 100));
    }

    /**
     * Updates the position along the bottom of the screen based on the
     * current position of the rect
     * 
     * @return 1 if the bar is at the edge of the screen, 0 otherwise
     */
    public int updatePosition() {
    	
		rect.offset((int) xPosition, 0);
		int ret = 0;
	
		if (rect.left < 0) {
		    rect.offsetTo(0, (int) panelHeight - 100 - barHeight);
		    ret = 1;
		} else if (rect.right > panelWidth) {
		    rect.offsetTo(panelWidth - barWidth, (int) panelHeight - 100
			    - barHeight);
		    ret = 1;
		}
		return ret;
    }

    /**
     * Checks for collision between the ball and bar, and returns result
     * 
     * @param boundingBox the collision box for the ball
     * @return Brick.LEFT_RIGHT for side collision, Brick.TOP_BOTTOM for
     * top and bottom collision, 0 for no collision.
     */
    public int checkCollision(Rect boundingBox) {
    	
		Rect intersection = boundingBox;
		boolean intersectsBrick = intersection.intersect(rect);
	
		if (intersectsBrick) {
		    if ((intersection.bottom - intersection.top) > (intersection.right - intersection.left)) {
			return Brick.LEFT_RIGHT;
		    } else {
			return Brick.TOP_BOTTOM;
		    }
		}
		
		return 0;
    }

    /**
     * Gets the rectangle that represents the bar object for drawing
     * 
     * @return android.graphics.Rect that represents this object
     * @see android.graphics.Rect
     */
    public Rect getRect() {
    	return rect;
    }
}
