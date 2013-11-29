package ca.brocku.cosc.BrickBreaker;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "a8961709_bbl";
    private static final String TABLE_NAME = "Leaderboards";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    private static final String[] COLUMNS = { KEY_SCORE, KEY_NAME };

    public MySQLHelper(Context context) {
	super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub

    }

    public void addScore(Score score) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_SCORE, score.getScore());
	values.put(KEY_NAME, score.getName());

	db.insert(TABLE_NAME, null, values);
	db.close();
    }

    public List<Score> getScores() {
	List<Score> scores = new LinkedList<Score>();

	String query = "SELECT * FROM " + TABLE_NAME;

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(query, null);

	Score score = null;
	if (cursor.moveToFirst()) {
	    do {
		score = new Score();
		score.setName(cursor.getString(1));
		score.setScore(Integer.parseInt(cursor.getString(0)));
		scores.add(score);
	    } while (cursor.moveToNext());
	}

	return scores;
    }
}
