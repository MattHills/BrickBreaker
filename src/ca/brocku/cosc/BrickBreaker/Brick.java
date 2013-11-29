package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick {

    public static final int SIDE_TOP = 1;
    public static final int SIDE_LEFT = 2;
    public static final int SIDE_RIGHT = 3;
    public static final int SIDE_BOTTOM = 4;

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

    public int checkCollision(Ball ball) {
    	System.out.println("collision check");
    	
		// top and bottom detection
		if (ball.xPosition + ball.radius >= rect.left && ball.xPosition - ball.radius <= rect.right) {
		    if (ball.yPosition + ball.radius >= rect.bottom && ball.yPosition - ball.radius <= rect.top) {
		    	//ball.setPosition(ball.xPosition, (int)(rect.bottom - ball.radius));
				System.out.println("Collision bottom");
				return Brick.SIDE_BOTTOM;
		    }
		}

		// left and right detection
		if (ball.yPosition + ball.radius >= rect.bottom && ball.yPosition - ball.radius <= rect.top) {
		    if (ball.xPosition + ball.radius >= rect.left && ball.xPosition - ball.radius <= rect.right) {
		    	//ball.setPosition((int)(rect.left - ball.radius), ball.yPosition);
				System.out.println("Collision left");
				return Brick.SIDE_LEFT;
		    }
		}

	return 0;
    }
}
