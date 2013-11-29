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

	ballRadius = panelHeight / 100;

	loadLevel();
    }

    private void loadLevel() {
	gameRunning = false;
	lives = STARTING_LIVES;

	bricks = new ArrayList<Brick>();
	bricks.add(new Brick(10, 10, brickWidth, brickHeight, Color.CYAN, 2));
	bricks.add(new Brick(35, 10 + brickHeight, brickWidth, brickHeight,
		Color.GREEN, 2));

	ball = new Ball(ballRadius);
	bar = new Bar();
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

    public void Draw(Canvas canvas) {

	if (!ball.isAlive) {
	    if (lives > 0) {
		ball = new Ball(ballRadius);
		gameRunning = false;
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
	    bar.initialize(panelWidth, panelHeight);
	    ball.initialize(panelWidth, panelHeight);
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
	    return true;
	}

	return false;
    }

    public void updateAccelerometer(float x) {

	barPos.x += x;
	// Log.w("positions", barPos.x + " " + x);
	if ((barPos.x + bar.barWidth / 2) > panelWidth)
	    barPos.x = panelWidth;

	if ((barPos.x - bar.barWidth / 2) < -panelWidth)
	    barPos.x = bar.barWidth / 2;

	// update ball class instance
	bar.xPosition = barPos.x;
	bar.yPosition = barPos.y;
	/**
	 * Remove 1 or 2 axis that are not needed, needs to be implemented with
	 * a real device. Will have to pass the values to bar as well
	 */

	if (gameRunning) {
	    bar.updatePosition();
	}
    }

}
