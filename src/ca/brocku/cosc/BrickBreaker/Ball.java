package ca.brocku.cosc.BrickBreaker;

import java.util.List;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Ball {

    int xPosition;
    int yPosition;

    boolean initialized;
    boolean isAlive;

    int radius;
    float xDirection;
    float yDirection;
    float speed;
    Rect boundingBox;

    int canvasHeight;
    int canvasWidth;

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
	
    public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
		boundingBox = new Rect(0, 0, radius*2, radius*2);
		boundingBox.offset(x - radius, y - radius);
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
