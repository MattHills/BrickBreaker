package ca.brocku.cosc.BrickBreaker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class GameOver extends Activity {

    private ListView topScores;
    private Button mainMenu;
    private Button newGame;
    private ArrayList<Score> globalScores;
    private CustomListAdapter adapter;

    /** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.game_over);
	this.globalScores = (ArrayList<Score>) getIntent()
		.getSerializableExtra("globalScores");

	topScores = (ListView) findViewById(R.id.listview_top_scores);
	mainMenu = (Button) findViewById(R.id.menu_button);
	newGame = (Button) findViewById(R.id.new_game);

	adapter = new CustomListAdapter(this, R.layout.listitems, globalScores);

	mainMenu.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent(GameOver.this, MainActivity.class);
		startActivity(intent);
	    }
	});

	newGame.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {

		Intent intent = new Intent(GameOver.this, Game.class);
		startActivity(intent);
	    }
	});

	/* onCreate */
    }

    protected void onStart() {
	super.onStart();

	// populate ListView topScores
    }
}
