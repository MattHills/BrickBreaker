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

    public int checkCollision(int ballPositionX, int ballPositionY,
	    float ballRadius) {
	// top and bottom detection
	if (ballPositionX >= rect.left && ballPositionX <= rect.right) {
	    if (ballPositionY >= rect.bottom) {
		// System.out.println("Collision bottom");
		// return Brick.SIDE_BOTTOM;
	    }

	    if (ballPositionY <= rect.top) {
		// System.out.println("Collision top");
		// return Brick.SIDE_TOP;
	    }
	}

	// left and right detection
	if (ballPositionY >= rect.bottom && ballPositionY <= rect.top) {
	    if (ballPositionX >= rect.left) {
		// System.out.println("Collision left");
		// return Brick.SIDE_LEFT;
	    }

	    if (ballPositionX <= rect.right) {
		// System.out.println("Collision right");
		// return Brick.SIDE_RIGHT;
	    }
	}

	return 0;
    }
}
