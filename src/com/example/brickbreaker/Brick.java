package com.example.brickbreaker;

import android.graphics.Rect;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick {
    int colour;
    Rect rect;
    int hitsRequired;
    int hitsTaken;

    public Brick(Rect rect, int colour, int hitsRequired) {
	
    	this.rect = rect;
		this.colour = colour;
		this.hitsRequired = hitsRequired;
		hitsTaken = 0;
    }
}
