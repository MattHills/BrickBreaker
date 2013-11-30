package ca.brocku.cosc.BrickBreaker;

import java.util.List;

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

    public Ball(float radius) {
	this.radius = radius;

	initialized = false;
	isAlive = true;

	speed = 4;
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

    public int updatePosition(List<Brick> bricks, Bar bar) {
	xPosition += speed * xDirection;
	yPosition += speed * yDirection;
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

	    collisionSide = bricks.get(i).checkCollision(xPosition, yPosition,
		    radius);

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
	collisionSide = bar.checkCollision(xPosition, yPosition, radius);

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
