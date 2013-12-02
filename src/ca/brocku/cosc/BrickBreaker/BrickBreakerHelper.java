package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;
import java.util.Collections;

public class BrickBreakerHelper {

    public ArrayList<Score> sortContacts(ArrayList<Score> scores) {
	ScoreComparator comp = new ScoreComparator();
	Collections.sort(scores, comp);
	return scores;
    }

}
