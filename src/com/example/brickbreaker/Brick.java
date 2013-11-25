package com.example.brickbreaker;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Brick {
    Position position;
    Colour colour;

    int width;
    int height;
    int hitsRequired;
    int hitsTaken;

    public Brick(Position position, Colour colour, int width, int height,
	    int hitsRequired) {
	this.position = position;
	this.colour = colour;
	this.width = width;
	this.height = height;
	this.hitsRequired = hitsRequired;
	hitsTaken = 0;
    }
}
