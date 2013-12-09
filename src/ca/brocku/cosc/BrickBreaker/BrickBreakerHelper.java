package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates and returns a sorted list of scores. Used to sort
 * scores and return them as a sorted list for displaying
 * on a scoreboard
 * 
 * @author Matt Hills
 */
public class BrickBreakerHelper {
	
    /**
     * Creates and returns sorted list of scores
     * @param scores unsorted list of scores
     * @return sorted list of scores, sorted by high score
     */
    public ArrayList<Score> sortScores(ArrayList<Score> scores) {
		ScoreComparator comp = new ScoreComparator();
		Collections.sort(scores, comp);
		return scores;
    }
}
