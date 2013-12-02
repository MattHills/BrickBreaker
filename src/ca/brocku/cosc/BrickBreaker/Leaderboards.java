package ca.brocku.cosc.BrickBreaker;

/* Matt Hills
 * 4640512
 * mh09wq
 * Cosc 3V97
 * Assignment 2
 * 
 * Friday, November 8th, 2013
 * ContactEdit.java
 */

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class Leaderboards extends Activity {

    private ArrayList<Score> localScores;
    private ArrayList<Score> globalScores;
    private Score highScore;
    private Spinner spinner;
    private ListView lv;
    private CustomListAdapter adapter;

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

	lv = (ListView) findViewById(R.id.listView);

	lv.setAdapter(adapter);

	// Get all the text fields
	spinner = (Spinner) findViewById(R.id.spinner1);

	ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(this,
		R.array.search_array, R.layout.custom_spinner);
	spinner.setAdapter(spinnerAdapter);
	addListenerOnSpinnerItemSelection();

    }

    public void setAdapter(ArrayList<Score> scores) {
	adapter = new CustomListAdapter(this, R.layout.listitems, scores,
		highScore);
	lv.setAdapter(adapter);
    }

    // Spinner onclick
    public void addListenerOnSpinnerItemSelection() {
	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

	    // Sets what extra field should be displayed
	    @Override
	    public void onItemSelected(AdapterView<?> arg0, View arg1,
		    int index, long arg3) {
		if (index == 1) {
		    setAdapter(globalScores);
		} else {
		    setAdapter(localScores);
		}
		// Notifies adapter to show the extra fields
		// adapter.notifyDataSetChanged();
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	    }

	});
    }

}
