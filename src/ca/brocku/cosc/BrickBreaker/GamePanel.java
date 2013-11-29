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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    // constants
    public static final int STARTING_LIVES = 3;

    // game objects
    private List<Brick> bricks;
    private Ball ball;
    private Bar bar;
    private Point size;

    // game options
    int panelWidth;
    int panelHeight;
    int brickWidth;
    int brickHeight;
    int brickPadding;
    int prevX;

    android.graphics.PointF barPos;
    public static float ballRadius;

    boolean gameRunning;

    int lives;
    int score;

    GameThread gameThread;

    // canvas items
    Paint paint = new Paint();

    public GamePanel(Context context, Point size) {
	super(context);
	this.size = size;

	barPos = new android.graphics.PointF();
	barPos.x = 0;
	barPos.y = 0;
	getHolder().addCallback(this);
	gameThread = new GameThread(getHolder(), this);

	setFocusable(true);
    }

    public void initializePanel(Canvas canvas) {

		panelWidth = size.x;
		panelHeight = size.y;
		// panelWidth = canvas.getWidth();
		// panelHeight = canvas.getHeight();
		brickWidth = panelWidth / 6;
		brickHeight = panelHeight / 30;
		brickPadding = panelWidth / 18;
	
		ballRadius = panelHeight / 100;
	
		loadLevel();
    }

    private void loadLevel() {
		gameRunning = false;
		lives = STARTING_LIVES;
	
		bricks = new ArrayList<Brick>();
	
		Brick b = new Brick(20, 20, brickWidth, brickHeight, Color.CYAN, 2);
		bricks.add(b);
		b = new Brick(50, 50, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		b = new Brick(200, 120, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		b = new Brick(240, 130, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		
		b = new Brick(250, 180, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		b = new Brick(360, 180, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		b = new Brick(250, 350, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		b = new Brick(360, 450, brickWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
		
		b = new Brick(0, 500, panelWidth, brickHeight, Color.GREEN, 2);
		bricks.add(b);
	
		ball = new Ball(ballRadius);
		ball.initialize(panelWidth, panelHeight);
	
		bar = new Bar();
		resetBar();
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
		// lives--;
	    } else {
		// end game
	    }
	}

	// background color
	canvas.drawColor(Color.DKGRAY);

	for (int i = 0; i < bricks.size(); i++) {
	    paint.setColor(bricks.get(i).colour);
	    canvas.drawRect(bricks.get(i).getRect(), paint);
	}

	paint.setColor(Ball.BALL_COLOUR);

	if (gameRunning) {
	    ball.updatePosition(bricks);
	    bar.updatePosition();
	} else {

	}

	// draw ball
	canvas.drawCircle(ball.xPosition, ball.yPosition, ball.radius, paint);

	// draw bar
	canvas.drawRect(bar.getRect(), paint);
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
