package com.example.brickbreaker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
	//constants
	public static final int STARTING_LIVES = 3;
	
	//game objects
	private List<Brick> bricks;
	private Ball ball;
	private Bar bar;
	
	//game options
	boolean gameRunning;
	int lives;
	int score;
	
	GameThread gameThread;

	//canvas items
	Paint paint = new Paint();
	
	public GamePanel(Context context) {
		super(context);	
	    	    
	    getHolder().addCallback(this);

	    gameThread = new GameThread(getHolder(), this);
	    
	    loadLevel();
	    setFocusable(true);
	}
	
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event){
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
	
	public void Draw(Canvas canvas)
	{		
		if(!ball.isAlive)
		{
			if(lives > 0)
			{
				ball = new Ball();
				gameRunning = false;
				//commented out for testing
				//lives--;
			}
			else
			{
				//end game
			}
		}
		
		
		//background color
		canvas.drawColor(Color.DKGRAY);
		
		for(int i = 0; i < bricks.size(); i++)
		{
			paint.setColor(bricks.get(i).colour);
			canvas.drawRect(bricks.get(i).rect, paint);
		}
		
		paint.setColor(Ball.BALL_COLOUR);
		
		if(gameRunning)
		{
			ball.updatePosition();
			bar.updatePosition();
		}
		else
		{
			bar.initialize(canvas.getWidth(), canvas.getHeight(), ball.radius);
			ball.initialize(canvas.getWidth(), canvas.getHeight());
		}
		
		//draw ball
		canvas.drawCircle(ball.xPosition, ball.yPosition, ball.radius, paint);
		
		//draw bar
		canvas.drawRect(bar.rect, paint);
	}


    private void loadLevel()
    {    	
    	gameRunning = false;
    	lives = STARTING_LIVES;
    	
	    bricks = new ArrayList<Brick>();
    	bricks.add(new Brick(new Rect(10, 10, 160, 40), Color.CYAN, 2));
    	bricks.add(new Brick(new Rect(40, 40, 190, 70), Color.GREEN, 2));
    	
    	ball = new Ball();
    	bar = new Bar();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
    	if(event.getAction() == MotionEvent.ACTION_DOWN && !gameRunning)
    	{
    		gameRunning = true;
    		return true;
    	}
    		
    	return false;
    }
    
}
