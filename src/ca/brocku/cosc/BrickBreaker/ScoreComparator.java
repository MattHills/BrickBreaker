package ca.brocku.cosc.BrickBreaker;

import java.util.Comparator;

/**
 * Custom comparator for comparing Score objects. Used for
 * sorting lists of Scores
 * 
 * @author Matt Hills
 */
public class ScoreComparator implements Comparator<Score> {

    @Override
    /**
     * Compares two scores and returns an int as the result
     * @returns negative integer if lhs is greater than rhs, positive otherwise
     */
    public int compare(Score lhs, Score rhs) {
		int ret;
		
		if (lhs.getScore() > rhs.getScore())
		    ret = -1;
		else
		    ret = 1;
		
		return ret;
    }
}
