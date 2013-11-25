package com.example.brickbreaker;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Position {
    private int x;
    private int y;

    public Position() {

    }

    public Position(int x, int y) {
	this.x = x;
	this.y = y;
    }

    int getX() {
	return x;
    }

    int getY() {
	return y;
    }

    void setX(int x) {
	this.x = x;
    }

    void setY(int y) {
	this.y = y;
    }
}
