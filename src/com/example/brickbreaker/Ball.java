package com.example.brickbreaker;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Ball {
    Position position;
    float radius = 5;

    public static final Colour colour = new Colour(200, 200, 200);

    public Ball() {
	position = new Position();
    }
}
