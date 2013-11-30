package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Bar {

    private Rect rect;
    float xPosition;
    float yPosition;

    int panelHeight;
    int panelWidth;
    int barHeight;
    int barWidth;
    int barCenter;

    boolean initialized;

    public Bar() {
	initialized = false;
    }

    public void setPosition(int x, int y) {
	xPosition = x;
	yPosition = y;
    }

    public void initialize(int panelWidth, int panelHeight) {
	this.panelWidth = panelWidth;
	this.panelHeight = panelHeight;
	barWidth = (panelWidth / 5);
	barHeight = panelHeight / 40;
	barCenter = barWidth / 2;

	rect = new Rect((int) xPosition - barWidth / 2,
		(int) (panelHeight - 100 - barHeight),
		(int) (xPosition + barWidth / 2), (int) (panelHeight - 100));

	// rect = new Rect((panelWidth / 2) - barCenter,
	// (int) (panelHeight - 140 + GamePanel.ballRadius),
	// (panelWidth / 2) + barCenter, (int) (panelHeight - 140
	// + GamePanel.ballRadius + barHeight));
    }

    public void updatePosition() {
	rect.offset((int) xPosition, 0);

	if (rect.left < 0) {
	    rect.offsetTo(0, (int) panelHeight - 100 - barHeight);
	} else if (rect.right > panelWidth) {
	    rect.offsetTo(panelWidth - barWidth, (int) panelHeight - 100
		    - barHeight);
	}
    }

    public int checkCollision(int xPosition, int yPosition, float radius) {
	// top and bottom detection
	if (xPosition + radius >= rect.left && xPosition - radius <= rect.right) {
	    if (yPosition - radius <= rect.bottom
		    && yPosition + radius >= rect.top) {
		// ball.setPosition(ball.xPosition, (int)(rect.bottom -
		// ball.radius));
		return Brick.TOP_BOTTOM;
	    }
	}

	// left and right detection
	if (yPosition + radius >= rect.bottom && yPosition - radius <= rect.top) {
	    if (xPosition - radius <= rect.left
		    && xPosition + radius >= rect.right) {
		// ball.setPosition((int)(rect.left - ball.radius),
		// ball.yPosition);
		return Brick.LEFT_RIGHT;
	    }
	}

	return 0;
    }

    public Rect getRect() {
	return rect;
    }
}
