package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    // constants
    public static final int STARTING_LIVES = 4;

    // game objects
    private List<Brick> bricks;
    private Ball ball;
    private Bar bar;
    private Point size;
    private ArrayList<Score> globalScores;
    private ArrayList<Score> localScores;
    private String name;

    // game options
    int panelWidth;
    int panelHeight;
    int brickWidth;
    int brickHeight;
    int brickPadding;
    int prevX;
    int barLines;
    int gameLevel;
    int difficulty;

    PointF barPos;
    public static int ballRadius;

    boolean gameRunning;

    int lives;
    int score;

    GameThread gameThread;

    // canvas items
    Paint paint;
    Paint antiAliasPaint;
    Paint textPaint;

    public GamePanel(Context context, Point size,
	    ArrayList<Score> globalScores, ArrayList<Score> localScores,
	    String name, int difficulty) {
	super(context);
	this.size = size;
	this.globalScores = globalScores;
	this.localScores = localScores;
	this.name = name;
	this.difficulty = difficulty;

	// initialize Paint objects for drawing objects
	paint = new Paint();
	antiAliasPaint = new Paint();
	antiAliasPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
	antiAliasPaint.setColor(Colour.BALL_COLOUR);
	textPaint = new Paint();
	textPaint.setColor(Color.WHITE);

	textPaint.setTextSize(50);

	lives = STARTING_LIVES;
	barLines = 0;
	gameLevel = 0;

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
	if (barLines < 12) {
	    barLines++;
	}
	bricks = new ArrayList<Brick>();

	Brick b;
	for (int i = 0; i < barLines; i++) {
	    numBricks = 1 + (int) (Math.random() * ((5 - 1) + 1));
	    brickMaxW = panelWidth / numBricks - brickPadding;
	    for (int j = brickPadding / 2; j < panelWidth - brickMaxW; j++) {
		hitsReq = 1 + (int) (Math.random() * ((3 - 1) + 1));
		b = new Brick(j, i * brickHeight + 5 * i, brickMaxW,
			brickHeight, getRandomColour(), hitsReq);
		bricks.add(b);
		j += brickMaxW + brickPadding;
	    }
	}

	ball = new Ball(ballRadius);
	ball.initialize(panelWidth, panelHeight);

	bar = new Bar();
	resetBar();

    }

    private int[] getRandomColour() {
	int index = 0 + (int) (Math.random() * ((7 - 0) + 1));

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
	    return Colour.BLUE1;
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

	bar.initialize(panelWidth, panelHeight, difficulty);
    }

    public void Draw(Canvas canvas) {

	if (bricks.isEmpty()) {
	    // user has beat level
	    loadLevel();
	}

	// if user has lives check to see if ball is dead
	if (lives > 0) {
	    if (!ball.isAlive) {

		ball = new Ball(ballRadius);
		ball.initialize(panelWidth, panelHeight);
		ball.initialize(panelWidth, panelHeight);
		barPos.x = 0;
		prevX = 0;
		gameRunning = false;

		resetBar();

		lives--;

	    }

	    drawObjects(canvas);
	} else {
	    Intent intent = new Intent(this.getContext(), GameOver.class);
	    intent.putExtra("globalScores", globalScores);
	    intent.putExtra("localScores", localScores);
	    Score newScore = new Score();
	    newScore.setName(name);
	    newScore.setScore(score);
	    newScore.setDate(new Date());
	    intent.putExtra("score", newScore);
	    this.getContext().startActivity(intent);
	    gameThread.stopThread();
	}

    }

    private void drawObjects(Canvas canvas) {
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
	}

	// draw ball
	canvas.drawCircle(ball.xPosition, ball.yPosition, ball.radius,
		antiAliasPaint);

	// draw bar
	paint.setColor(Colour.BALL_COLOUR);

	canvas.drawRect(bar.getRect(), paint);

	canvas.drawText("Lives: " + lives, 0, panelHeight - 50, textPaint);
	canvas.drawText("Score: " + score, panelWidth - 400, panelHeight - 50,
		textPaint);
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
		barPos.x += x * 2;
	    } else {
		if (x < 0)
		    barPos.x = -15;
		else
		    barPos.x = 15;
	    }

	    bar.xPosition = barPos.x;

	    if (gameRunning) {
		int side = bar.updatePosition();
		if (side != 0) {
		    prevX *= -1;
		}
	    }
	}
    }
}
