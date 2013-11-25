package com.example.brickbreaker;

import java.util.Date;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Score {
    int score;
    String name;
    Date date;

    public int getScore() {
	return score;
    }

    public void setScore(int score) {
	this.score = score;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }
}
