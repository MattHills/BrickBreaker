package ca.brocku.cosc.BrickBreaker;
 
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Thread that takes care of running the canvas for a brick breaker
 * game. While the thread is running, the current GamePanel's 
 * Draw method is called to redraw the screen
 * 
 * @author Dan Lapp
 */
public class GameThread extends Thread
{
	private SurfaceHolder surfaceHolder;
	
	// the panel that draws the objects on the screen
	private GamePanel gamePanel;
	
	//is the game thread running or not
	private boolean isRunning;
	
	/**
	 * Create a new GameThread. Save all passed parameters
	 * @param surfaceHolder the holder for gamePanel's canvas
	 * @param gamePanel the instance of GamePanel that draws the objects
	 * and runs the game
	 */
	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
	{
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		isRunning = false;
	}
	
	/**
	 * Start this GameThread, game starts
	 */
	public void startThread()
	{
		isRunning = true;
	}
	
	/**
	 * Stop this thread, ball has died
	 */
	public void stopThread()
	{
		isRunning = false;
	}
	
	/* (non-Javadoc)
	 * This thread continuously calls gamePanel.Draw to update the screen
	 * constantly 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		Canvas canvas = null;
		
		/* initialize the window and wait for user */
		try {
            canvas = surfaceHolder.lockCanvas(null);
            synchronized (surfaceHolder) {
            	if(canvas != null){
            		gamePanel.initializePanel();
              	}
            	
            }
        } finally {
            /* make sure unlockCanvasAndPost
             * is called, so canvas does not stay locked
             */
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
			
		/* while game thread is running, redraw screen */
		while(isRunning)
		{
			canvas = null;
			
			try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                	if(canvas == null)
                		break;
                	
                    gamePanel.Draw(canvas);
                }
            } finally {
                /* if exception is thrown, unlockCanvasAndPost
                 * is called, so canvas does not stay locked
                 */
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
		}
	}
}
