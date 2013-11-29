package ca.brocku.cosc.BrickBreaker;

/* Matt Hills
 * 4640512
 * mh09wq
 * Cosc 3V97
 * Assignment 2
 * 
 * This allows for comparing contacts so they can be sorted
 * 
 * Friday, November 8th, 2013
 * ContactNameComparator.java
 */

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {

    @Override
    public int compare(Score lhs, Score rhs) {
	int ret;
	if (lhs.getScore() > rhs.getScore())
	    ret = 1;
	else
	    ret = -1;
	return ret;
    }
}
