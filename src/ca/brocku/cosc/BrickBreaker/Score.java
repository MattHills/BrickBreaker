package ca.brocku.cosc.BrickBreaker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dl08ti on 18/11/13.
 */
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
	return "Score [name=" + name + " , score=" + score + "]";
    }

}
