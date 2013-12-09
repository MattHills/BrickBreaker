package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Leaderboard activity that displays all the high scores on a ListView on the
 * screen. Displays local user scores and global scores uploaded to the
 * highscore database
 * 
 * @author Matt Hills
 */
public class Leaderboards extends Activity {

    // score data
    private ArrayList<Score> localScores;
    private ArrayList<Score> globalScores;
    private Score highScore;

    // activity layout widgets
    private Spinner spinner;
    private ListView listView;
    private CustomListAdapter adapter;

    /*
     * (non-Javadoc) Populates the listview with the high scores and sets up
     * other widgets
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	localScores = (ArrayList<Score>) getIntent().getSerializableExtra(
		"localScores");
	globalScores = (ArrayList<Score>) getIntent().getSerializableExtra(
		"globalScores");
	highScore = (Score) getIntent().getSerializableExtra("highScore");
	setContentView(R.layout.activity_leaderboards);

	adapter = new CustomListAdapter(this, R.layout.listitems, localScores,
		highScore);

	listView = (ListView) findViewById(R.id.listView);

	listView.setAdapter(adapter);

	// Get all the text fields
	spinner = (Spinner) findViewById(R.id.spinner1);

	ArrayAdapter<?> spinnerAdapter;

	// If the user turns off online leaderboards, globalScores will be null
	// This just saves a SharedPreferences call
	if (null == globalScores) {
	    spinnerAdapter = ArrayAdapter.createFromResource(this,
		    R.array.search_array, R.layout.custom_spinner);
	} else {
	    spinnerAdapter = ArrayAdapter.createFromResource(this,
		    R.array.search_array_with_global, R.layout.custom_spinner);
	}
	spinner.setAdapter(spinnerAdapter);
	addListenerOnSpinnerItemSelection();

    }

    /**
     * Creates a new CustomListAdapter with the list of high scores, and sets
     * the listview adapter
     * 
     * @param scores
     *            the list of scores to display on the listview
     */
    public void setAdapter(ArrayList<Score> scores) {
	adapter = new CustomListAdapter(this, R.layout.listitems, scores,
		highScore);
	listView.setAdapter(adapter);
    }

    /**
     * Spinner on item selected listener. When the user selects global or local
     * scores, the ListView is populated with the approriate list of scores
     */
    public void addListenerOnSpinnerItemSelection() {
	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

	    // Sets what extra field should be displayed
	    @Override
	    public void onItemSelected(AdapterView<?> arg0, View arg1,
		    int index, long arg3) {
		if (index == 1 && null != globalScores) {
		    setAdapter(globalScores);
		} else {
		    setAdapter(localScores);
		}
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	    }

	});
    }
}
