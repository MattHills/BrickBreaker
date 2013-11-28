package ca.brocku.cosc.BrickBreaker;

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

		speed = 5;
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

	public void updatePosition() {
		xPosition += speed * xDirection;
		yPosition += speed * yDirection;

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
	}
}
