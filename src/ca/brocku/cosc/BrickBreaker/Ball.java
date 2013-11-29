package ca.brocku.cosc.BrickBreaker;

import java.util.List;

import android.graphics.Color;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Ball {

    int xPosition;
    int yPosition;

    boolean initialized;
    boolean isAlive;

    float radius;
    float xDirection;
    float yDirection;
    float speed;

    int canvasHeight;
    int canvasWidth;

    public static final int BALL_COLOUR = Color.LTGRAY;

    public Ball(float radius) {
	this.radius = radius;

	initialized = false;
	isAlive = true;

	speed = 2;
	xDirection = -3;
	yDirection = -3;
    }

    public void setPosition(int x, int y) {
	xPosition = x;
	yPosition = y;
    }

    public float getSpeed() {
	return speed;
    }

    public void setSpeed(float speed) {
	this.speed = speed;
    }

    public void initialize(int canvasWidth, int canvasHeight) {
	initialized = true;
	this.canvasWidth = canvasWidth;
	this.canvasHeight = canvasHeight;
	setPosition(canvasWidth / 2, canvasHeight - 140);
    }

    public void updatePosition(List<Brick> bricks) {
	xPosition += speed * xDirection;
	yPosition += speed * yDirection;

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
	    int collisionSide = bricks.get(i).checkCollision(xPosition,
		    yPosition, radius);

	    switch (collisionSide) {
	    case Brick.SIDE_BOTTOM:
		// Log.d("Collision", "bottom " + xPosition + " " + yPosition);
		yDirection *= -1.0;
		break;

	    case Brick.SIDE_TOP:
		// Log.d("Collision",
		// "top " + xPosition + " " + yPosition + " Brick: "
		// + bricks.get(i).xPosition + ", "
		// + bricks.get(i).yPosition);
		yDirection *= -1.0;
		break;

	    case Brick.SIDE_RIGHT:
		// Log.d("Collision", "right " + xPosition + " " + yPosition);
		xDirection *= -1.0;
		break;

	    case Brick.SIDE_LEFT:
		// Log.d("Collision", "left " + xPosition + " " + yPosition);
		xDirection *= -1.0;
		break;

	    default:
		break;
	    }
	}

	/* Collision with bar */
    }
}
