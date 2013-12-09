package ca.brocku.cosc.BrickBreaker;

import java.util.List;

import android.graphics.Rect;


/**
 * Ball class. This class represents
 * 
 * @author Dan Lapp, Matt Hills
 *
 */
public class Ball {

	//current position and direction
    int xPosition;
    int yPosition;
    float xDirection;
    float yDirection;
    float speed;

    //state of ball
    boolean initialized;
    boolean isAlive;

    //size and collision box
    int radius;
    Rect boundingBox;

    //max height and width of device screen
    int canvasHeight;
    int canvasWidth;

    /**
     * Constructor to create a new ball
     * 
     * @param radius the ball radius in pixels
     */
    public Ball(int radius) {
		this.radius = radius;
	
		initialized = false;
		isAlive = true;
	
		speed = 3;
		xDirection = -3;
		yDirection = -3;
		
		boundingBox = new Rect(0, 0, radius*2, radius*2);
		boundingBox.offset(xPosition, yPosition);
	}
	
    /**
     * Set the position of the ball to one position
     * 
     * @param x the new x position
     * @param y the new y position
     */
    public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
		boundingBox = new Rect(0, 0, radius*2, radius*2);
		boundingBox.offset(x - radius, y - radius);
    }

    /**
     * Get current ball speed
     * 
     * @return speed of ball 
     */
    public float getSpeed() {
    	return speed;
    }

    /**
     * Set the speed of the ball
     * 
     * @param speed the new speed of the ball
     */
    public void setSpeed(float speed) {
	this.speed = speed;
    }

    /**
     * Initialize the ball's values to their initial state.
     * Sets the ball to the starting position
     * 
     * @param canvasWidth the width of the device screen
     * @param canvasHeight the height of the device screen
     */
    public void initialize(int canvasWidth, int canvasHeight) {
		initialized = true;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		setPosition(canvasWidth / 2, canvasHeight - 140);
    }
    
    /**
     * Update the position of the ball. This moves the ball's position
     * for one frame. New position is based on direction and speed.
     * Also detects collision with the bricks, bar, and edges of the
     * screen
     * 
     * @param bricks current bricks on screen
     * @param bar the current instance of the bar on the screen
     * @return 1 if the ball collides with a brick, 0 otherwise
     */
    public int updatePosition(List<Brick> bricks, Bar bar) {
    	//update position
		xPosition += speed * xDirection;
		yPosition += speed * yDirection;
		
		//calculate collision box for this ball
		boundingBox = new Rect(0, 0, radius*2, radius*2);
		boundingBox.offset(xPosition - radius, yPosition - radius);	
		System.out.println(boundingBox.left + " " + boundingBox.top);
		
		int collisionSide, ret = 0;
	
		/* Collision with walls and bottom of screen */
		if (xPosition >= (canvasWidth - radius)) {
		    xPosition = (int) (canvasWidth - radius);
		    xDirection *= -1.0;
		} else if (xPosition <= radius) {
		    xPosition = (int) radius;
		    xDirection *= -1.0;
		}
	
		if (yPosition >= (canvasHeight - radius)) {
		    isAlive = false;
		} else if (yPosition <= radius) {
		    yPosition = (int) radius;
		    yDirection *= -1.0;
		}
	
		/* Collision with bricks */
		for (int i = 0; i < bricks.size(); i++) {
	
		    collisionSide = bricks.get(i).checkCollision(boundingBox);
	
		    switch (collisionSide) {
			    case Brick.TOP_BOTTOM:
				yDirection *= -1.0;
				ret = 1;
				break;
		
			    case Brick.LEFT_RIGHT:
				xDirection *= -1.0;
				ret = 1;
				break;
				
			    default:
				break;
			}
		}

		/* Collision with bar */
		collisionSide = bar.checkCollision(boundingBox);
	
		switch (collisionSide) {
		case Brick.TOP_BOTTOM:
		    yDirection *= -1.0;
		    break;
	
		case Brick.LEFT_RIGHT:
		    xDirection *= -1.0;
		    break;
		default:
		    break;
		}
		
		return ret;
	}
}
