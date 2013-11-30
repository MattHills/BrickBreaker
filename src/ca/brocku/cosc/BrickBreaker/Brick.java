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

    int colour;
    int hitsRequired;
    int hitsTaken;

    public Brick(int xPosition, int yPosition, int brickWidth, int brickHeight,
	    int colour, int hitsRequired) {

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

    public int checkCollision(int xPosition, int yPosition, float radius) {
    	System.out.println(xPosition + "  "+ yPosition);
		// top and bottom detection
		if (xPosition + radius >= rect.left && xPosition - radius <= rect.right) {
		    if (yPosition - radius <= rect.bottom && yPosition + radius >= rect.top) {
		    	//ball.setPosition(ball.xPosition, (int)(rect.bottom - ball.radius));
		    	onCollision();
				return Brick.TOP_BOTTOM;
		    }
		}

		// left and right detection
		if (yPosition + radius >= rect.bottom && yPosition - radius <= rect.top) {
		    if (xPosition - radius <= rect.left && xPosition + radius >= rect.right) {
		    	//ball.setPosition((int)(rect.left - ball.radius), ball.yPosition);
		    	onCollision();
				return Brick.LEFT_RIGHT;
		    }
		}

		return 0;
    }
    
    private void onCollision()
    {
    	//do something on collision
    }
}
