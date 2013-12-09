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

/** The GamePanel class is the main class for the brick breaker game. It is
 * responsible for the main game loop, and drawing the objects to the canvas.
 * It moves the ball and bar, updates bricks, keeps track of game state,
 * and updates score. It also loads each level.
 * 
 * @author Dan Lapp, Matt Hills
 *
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    // constants
    public static final int STARTING_LIVES = 3;

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

    // position of the bar, and ball radius
    PointF barPos;
    public static int ballRadius;

    boolean gameRunning; //is the game running or is it waiting for user

    //lives, total score for game, score for each hit of a brick
    int lives;
    int score;
    int brickScore;

    // the thread that continuously calls this.Draw()
    GameThread gameThread;

    // canvas items
    Paint paint;
    Paint antiAliasPaint;
    Paint textPaint;

    /**
     * Initializes the game panel. Initializes screen size and score variables.
     * Sets up score based on game difficulty. Initializes canvas paint objects.  
     * Starts a new GameThread, which handles the screen drawing loop. It calls
     * GamePanel.Draw to update the screen
     * 
     * @param context the current app context
     * @param size maximum screen dimensions
     * @param globalScores list of global scores from server
     * @param localScores list of local scores from device
     * @param name player name
     * @param difficulty difficulty setting
     */
    public GamePanel(Context context, Point size,
	    ArrayList<Score> globalScores, ArrayList<Score> localScores,
	    String name, int difficulty) {
    	
		super(context);
		this.size = size;
		this.globalScores = globalScores;
		this.localScores = localScores;
		this.name = name;
		this.difficulty = difficulty;
		
		/* set the score for each brick based on difficulty */
		switch(difficulty)
		{
		case Game.EASY_DIFFICULTY:
			brickScore = Brick.SCORE_EASY;
			break;
		
		case Game.MEDIUM_DIFFICULTY:
			brickScore = Brick.SCORE_MED;
			break;
		
		case Game.HARD_DIFFICULTY:
			brickScore = Brick.SCORE_HARD;
			break;
		}
	
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
		
		//start a new GameThread to handle the screen redraws
		gameThread = new GameThread(getHolder(), this);
	
		setFocusable(true);
    }

    /**
     * Initializes the panel so that the game loop can draw to the screen.
     * Sets up maximum screen extents as variables, and calculates proper
     * brick and ball sizes based on the screen resolution of the device.
     * Then load the first level.
     */
    public void initializePanel() {
		panelWidth = size.x;
		panelHeight = size.y;
	
		brickHeight = panelHeight / 30;
		brickPadding = panelWidth / 18;
	
		ballRadius = panelHeight / 100;
	
		//load first level
		loadLevel();
    }

    /**
     * Loads each level. Uses variable 'gameLevel' to keep track of how
     * many levels the user has beaten and creates the number of bricks
     * accordingly. Higher the level the more bricks there are. 
     */
    private void loadLevel() {
		gameRunning = false;
		int numBricks, brickMaxW, hitsReq;
	
		gameLevel++;
		
		//create max number of lines
		if (barLines < 12) {
		    barLines++;
		}
		
		bricks = new ArrayList<Brick>();
		Brick b;
		
		//randomize the number, colour and position of bricks
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
	
		//reset the ball and place at starting point
		ball = new Ball(ballRadius);
		ball.initialize(panelWidth, panelHeight);
	
		//reset bar and put at starting point
		bar = new Bar();
		resetBar();

    }
    
    /**
     * Returns a random constant colour array from class Colour. Used to
     * randomly colour the bricks
     * 
     * @return random colour constant from Colour
     * @see Colour.java
     */
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

    /**
     * Resets the bar to its starting point in the middle of the screen
     */
    private void resetBar() {
    	bar.setPosition(panelWidth / 2, panelHeight / 8);
		bar.initialize(panelWidth, panelHeight, difficulty);
    }

    /**
     * Clears screen and redraws objects in their current position. 
     * Checks to see if the level is beaten, the ball is dead, or if
     * the game is over. If game over, start a GameOver activity
     * 
     * @param canvas the canvas to draw the objects to
     */
    public void Draw(Canvas canvas) {

    	// user has beat level, add a life and create new level 
		if (bricks.isEmpty()) {
			lives++;
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
			//game over, start GameOver activity and pass it the score
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

    /**
     * Called by GamePanel.Draw to draw all screen objects to the canvas
     * 
     * @param canvas the canvas to draw the objects to
     */
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
			score += brickScore;
	
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

    
    /**
     * Updates bar position based on new accelerometer data
     * 
     * @param x accelerometer force along the x axis
     */
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
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && !gameRunning) {
		    gameRunning = true;
		    prevX = 0;
		    return true;
		}
	
		return false;
    }

}
