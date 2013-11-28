package ca.brocku.cosc.BrickBreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Bar {

    private Rect rect;
    int xPosition;
    int yPosition;
    
    int panelHeight;
    int panelWidth;
    int barHeight;
    int barWidth;
    int barCenter; 
    
    boolean initialized;
    
    public Bar()
    {
    	initialized = false;
    }

	public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
	}

    
    public void initialize(int panelWidth, int panelHeight)
    {
    	this.panelWidth = panelWidth;
    	this.panelHeight = panelHeight;
    	barWidth = (panelWidth / 5);
    	barHeight = panelHeight / 35;
    	barCenter = barWidth / 2;
    	
    	rect = new Rect((panelWidth / 2) - barCenter, 
    			(int)(panelHeight - 140 + GamePanel.ballRadius), 
    			(panelWidth / 2) + barCenter,
    			(int)(panelHeight - 140 + GamePanel.ballRadius + barHeight));
     }
    
    public void updatePosition()
    {
    	
    }
    
    public Rect getRect()
    {
    	return rect;
    }
}
