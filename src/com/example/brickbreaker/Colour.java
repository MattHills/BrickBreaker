package com.example.brickbreaker;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Colour {
    private int r, g, b;

    public Colour(int r, int g, int b) {
	this.setR(r);
	this.setG(g);
	this.setB(b);
    }

    void setR(int r) {
	if (r < 0)
	    this.r = 0;
	else if (r > 255)
	    this.r = 255;
	else
	    this.r = r;
    }

    void setG(int g) {
	if (g < 0)
	    this.g = 0;
	else if (g > 255)
	    this.g = 255;
	else
	    this.g = g;
    }

    void setB(int b) {
	if (b < 0)
	    this.b = 0;
	else if (b > 255)
	    this.b = 255;
	else
	    this.b = b;
    }

    int getR() {
	return r;
    }

    int getG() {
	return g;
    }

    int getB() {
	return b;
    }
}
