package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    // constants
    public static final int STARTING_LIVES = 3;

    // game objects
    private List<Brick> bricks;
    private Ball ball;
    private Bar bar;
    private Point size;
    private TextView livesView;
    private TextView scoreView;
    private Canvas canvas;

    // game options
    int panelWidth;
    int panelHeight;
    int brickWidth;
    int brickHeight;
    int brickPadding;
    int prevX;
    int barLines;
    int gameLevel;

    android.graphics.PointF barPos;
    public static float ballRadius;

    boolean gameRunning;

    int lives;
    int score;

    GameThread gameThread;

    // canvas items
    Paint paint;
    Paint antiAliasPaint;

    public GamePanel(Context context, Point size) {
	super(context);
	this.size = size;

	paint = new Paint();
	antiAliasPaint = new Paint();
	antiAliasPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
	antiAliasPaint.setColor(Colour.BALL_COLOUR);

	lives = STARTING_LIVES;
	barLines = 1;
	gameLevel = 0;

	barPos = new android.graphics.PointF();
	barPos.x = 0;
	barPos.y = 0;
	getHolder().addCallback(this);
	gameThread = new GameThread(getHolder(), this);

	setFocusable(true);
    }

    public void initializePanel(Canvas canvas) {

	this.canvas = canvas;
	panelWidth = size.x;
	panelHeight = size.y;

	// brickWidth = panelWidth / 6;
	brickHeight = panelHeight / 30;
	brickPadding = panelWidth / 18;

	ballRadius = panelHeight / 100;

	loadLevel();
    }

    private void loadLevel() {
	gameRunning = false;
	int numBricks, brickMaxW, hitsReq;

	gameLevel++;
	bricks = new ArrayList<Brick>();

	Brick b;
	for (int i = 0; i < barLines; i++) {
	    numBricks = 1 + (int) (Math.random() * ((5 - 1) + 1));
	    brickMaxW = panelWidth / numBricks - brickPadding;
	    for (int j = brickPadding / 2; j < panelWidth - brickMaxW; j++) {
		hitsReq = 1 + (int) (Math.random() * ((gameLevel - 1) + 1));
		b = new Brick(j, i * brickHeight, brickMaxW, brickHeight,
			getRandomColour(), hitsReq);
		bricks.add(b);
		j += brickMaxW + brickPadding;
	    }
	}

	// Brick b = new Brick(20, 20, brickWidth, brickHeight, Colour.BLUE1,
	// 2);
	// bricks.add(b);
	// b = new Brick(50, 50, brickWidth, brickHeight, Colour.BLUE2, 2);
	// bricks.add(b);
	// b = new Brick(200, 120, brickWidth, brickHeight, Colour.GREEN1, 2);
	// bricks.add(b);
	// b = new Brick(240, 130, brickWidth, brickHeight, Colour.ORANGE, 2);
	// bricks.add(b);
	//
	// b = new Brick(360, 180, brickWidth, brickHeight, Colour.PURPLE, 2);
	// bricks.add(b);
	// b = new Brick(250, 350, brickWidth, brickHeight, Colour.RED, 2);
	// bricks.add(b);
	// b = new Brick(360, 450, brickWidth, brickHeight, Colour.YELLOW, 2);
	// bricks.add(b);

	ball = new Ball(ballRadius);
	ball.initialize(panelWidth, panelHeight);

	bar = new Bar();
	resetBar();

    }

    private int[] getRandomColour() {
	int index = 0 + (int) (Math.random() * ((8 - 0) + 1));

	switch (index) {
	case 0:
	    return Colour.BLUE1;
	case 1:
	    return Colour.BLUE2;
	case 2:
	    return Colour.GREEN1;
	case 3:
	    return Colour.GREEN2;
	case 4:
	    return Colour.ORANGE;
	case 5:
	    return Colour.PURPLE;
	case 6:
	    return Colour.RED;
	case 7:
	    return Colour.YELLOW;
	default:
	    return Colour.BLACK;
	}
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
	if (keycode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    gameRunning = false;
	    gameThread.stopThread();
	    return true;
	}

	return super.onKeyDown(keycode, event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	// TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
	gameThread.startThread();
	gameThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
	gameThread.stopThread();
    }

    private void resetBar() {

	bar.setPosition(panelWidth / 2, panelHeight / 8);
	bar.initialize(panelWidth, panelHeight);
    }

    public void Draw(Canvas canvas) {

	if (!ball.isAlive) {
	    if (lives > 0) {
		ball = new Ball(ballRadius);
		ball.initialize(panelWidth, panelHeight);
		ball.initialize(panelWidth, panelHeight);
		barPos.x = 0;
		prevX = 0;
		gameRunning = false;

		resetBar();

		// commented out for testing
		lives--;
	    } else {
		loadLevel();
	    }
	}

	// background color
	canvas.drawColor(Color.DKGRAY);

	// check to see if any bricks are to be removed
	for (int i = 0; i < bricks.size(); i++) {
	    if (bricks.get(i).hitsRequired <= bricks.get(i).hitsTaken)
		bricks.remove(i);
	}

	for (int i = 0; i < bricks.size(); i++) {
	    paint.setColor(bricks.get(i).getColour());
	    canvas.drawRect(bricks.get(i).getRect(), paint);
	}

	if (gameRunning) {
	    int brickHit = 0;
	    brickHit = ball.updatePosition(bricks, bar);
	    if (brickHit != 0) {
		score += 10;

	    }
	    bar.updatePosition();
	} else {

	}

	// draw ball
	canvas.drawCircle(ball.xPosition, ball.yPosition, ball.radius,
		antiAliasPaint);

	// draw bar
	canvas.drawRect(bar.getRect(), paint);

	Paint text = new Paint();
	text.setColor(Color.WHITE);
	text.setTextSize(40);
	canvas.drawText("Lives: " + lives, 0, panelHeight - 50, text);
	canvas.drawText("Score: " + score, panelWidth - 300, panelHeight - 50,
		text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	if (event.getAction() == MotionEvent.ACTION_DOWN && !gameRunning) {
	    gameRunning = true;
	    prevX = 0;
	    return true;
	}

	return false;
    }

    public void updateAccelerometer(float x) {

	if (bar != null) {
	    if (prevX * x < 0) {
		// barPos.x *= -1;
	    }
	    if (prevX * x > 0) {
		if (prevX < 0) {
		    if (prevX < x) {
			x *= -1;
		    }
		} else {
		    if (prevX > x) {
			x *= -1;
		    }
		}
	    }

	    prevX = (int) x;

	    if (Math.abs(barPos.x + x) < 15) {
		barPos.x += x;
	    } else {
		if (x < 0)
		    barPos.x = -15;
		else
		    barPos.x = 15;
	    }

	    bar.xPosition = barPos.x;

	    if (gameRunning) {
		bar.updatePosition();
	    }
	}
    }
}
