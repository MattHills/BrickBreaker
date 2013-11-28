package ca.brocku.cosc.BrickBreaker;
 
import android.graphics.Canvas;
import android.view.SurfaceHolder;

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
		Canvas canvas = null;
		
		try {
            canvas = surfaceHolder.lockCanvas(null);
            synchronized (surfaceHolder) {
            	if(canvas != null){
            		gamePanel.initializePanel(canvas);
              	}
            	
            }
        } finally {
            /* if exception is thrown, unlockCanvasAndPost
             * is called, so canvas does not stay locked
             */
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
				
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
