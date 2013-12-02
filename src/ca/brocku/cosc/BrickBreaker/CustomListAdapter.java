package ca.brocku.cosc.BrickBreaker;

/* Matt Hills
 * 4640512
 * mh09wq
 * Cosc 3V97
 * Assignment 2
 * 
 * This adapter is used to display the contacts in a list
 * Allows for displaying extra data in the list
 * 
 * Friday, November 8th, 2013
 * CustomListAdapter.java
 */

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class CustomListAdapter extends ArrayAdapter<Score> {

    private int resource;

    private ArrayList<Score> scores;

    public CustomListAdapter(Context context, int resource,
	    ArrayList<Score> scores) {
	super(context, resource, scores);
	this.scores = scores;
	this.resource = resource;
    }

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
	}
	return scoreView;
    }
}
