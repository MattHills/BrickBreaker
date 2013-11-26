package com.example.brickbreaker;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameThread extends Thread
{
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	private boolean isRunning;
	
	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
	{
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		isRunning = false;
	}
	
	public void startThread()
	{
		isRunning = true;
	}
	
	public void stopThread()
	{
		isRunning = false;
	}
	
	@Override
	public void run()
	{
		Canvas canvas;
		
		while(isRunning)
		{
			canvas = null;
			
			try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    gamePanel.onDraw(canvas);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
		}
	}
}
