package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A custom list adapter that is used to create a custom View for displaying
 * high scores
 * 
 * @author Matt Hills
 */
@SuppressLint("DefaultLocale")
public class CustomListAdapter extends ArrayAdapter<Score> {

    private int resource;

    private ArrayList<Score> scores;
    private Score newScore;

    /**
     * Creates a new CustomListAdapter that extents ArrayAdapter. Added
     * functionality includes the list of scores and a new highscore to add
     * 
     * @param context
     *            current app context
     * @param resource
     *            view resource
     * @param scores
     *            list of high scores for this view
     * @param newScore
     *            new score to add to the list of high scores
     */
    public CustomListAdapter(Context context, int resource,
	    ArrayList<Score> scores, Score newScore) {

	super(context, resource, scores);
	this.scores = scores;
	this.resource = resource;
	this.newScore = newScore;
    }

    /*
     * (non-Javadoc) Populates the TextView with the high scores to be displayed
     * 
     * @see android.widget.ArrayAdapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

	LinearLayout scoreView;
	Score score = scores.get(index);

	if (convertView == null) {

	    scoreView = new LinearLayout(getContext());
	    LayoutInflater vi;
	    vi = (LayoutInflater) getContext().getSystemService(
		    Context.LAYOUT_INFLATER_SERVICE);
	    vi.inflate(resource, scoreView, true);
	} else {
	    scoreView = (LinearLayout) convertView;
	}

	if (score != null) {

	    TextView alertText = (TextView) scoreView
		    .findViewById(R.id.txtName);

	    alertText.setText(index + 1 + " " + score.getName() + ": "
		    + score.getScore());

	    // Checking to see if the score is the new score added,
	    // if it is it will be highlighted gray.
	    if ((score.getDate() != null && score.getDate().equals(
		    newScore.getDate()))
		    && score.getName().equals(newScore.getName())
		    && score.getScore() == newScore.getScore()) {

		alertText.setBackgroundColor(Color.GRAY);
	    } else {
		alertText.setBackgroundColor(Color.TRANSPARENT);
	    }

	}

	return scoreView;
    }
}
