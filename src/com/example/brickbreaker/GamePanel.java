package com.example.brickbreaker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
	//game objects
	private List<Brick> bricks;
	private Ball ball;
	boolean gameRunning;
	
	GameThread gameThread;

	Paint paint = new Paint();
	RectF r = new RectF(50.0f, 22.0f, 100.0f, 200.0f);
	
	public GamePanel(Context context) {
		super(context);	
	    
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
	    
	    getHolder().addCallback(this);

	    gameThread = new GameThread(getHolder(), this);
	    
	    loadLevel();
	    setFocusable(true);
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
	
	@Override
	public void onDraw(Canvas canvas)
	{		
		canvas.drawColor(Color.BLUE);
		
		for(int i = 0; i < bricks.size(); i++)
		{
			paint.setColor(bricks.get(i).colour);
			canvas.drawRect(bricks.get(i).rect, paint);
		}
		
		paint.setColor(Ball.BALL_COLOUR);
		
		if(!gameRunning)
			ball.setPosition(canvas.getWidth()/2, canvas.getHeight() - 50);
		else
		{
			Position p = new Position(ball.position.getX(), ball.position.getY() - 4);
			ball.setPosition(p);
		}
		
		canvas.drawCircle(ball.getPosition().getX(), ball.getPosition().getY(), ball.radius, paint);
	}


    private void loadLevel()
    {
    	gameRunning = false;
    	
	    bricks = new ArrayList<Brick>();
    	bricks.add(new Brick(new Rect(10, 10, 160, 40), Color.CYAN, 2));
    	bricks.add(new Brick(new Rect(40, 40, 190, 70), Color.GREEN, 2));
    	
    	ball = new Ball();
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
