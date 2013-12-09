package ca.brocku.cosc.BrickBreaker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author Matt Hills
 * 
 *         This class connects to sends a call to a server php file, which then
 *         connects to the global database for this app. Depending on what uses
 *         this class, it either adds to the database or retrieves the scores
 *         that are already uploaded.
 * 
 *         No libraries were imported into our project for this, it is all
 *         standard Java libraries.
 * 
 *         Online tutorials for MySql connections were found online and modified
 *         for my own personal use.
 * 
 *         http://stackoverflow.com/questions/15027210/connect-to-mysql-database
 *         -from- android
 *         http://www.helloandroid.com/tutorials/connecting-mysql-database
 * 
 *         Were two helpful resources.
 * 
 * @author Matt Hills
 * 
 */

public class JSONFunctions extends AsyncTask<String, String, JSONArray> {
    // Database retrieval variables
    private Score uploadScore;
    private JSONArray j;
    private Handler handler;

    public void setHandler(Handler handler) {
	this.handler = handler;
    }

    /**
     * Sets the uploadScore if called from game over the score will be added to
     * the database if it is not null
     * 
     * @param uploadScore
     */
    public void setUploadScore(Score uploadScore) {
	this.uploadScore = uploadScore;
    }

    /*
     * Urls is supposed to take in a list of urls that will be called on the
     * post but we are using a static server, so we do not need them.
     * 
     * This method inserts into the database and retrieves scores already in the
     * table.
     * 
     * 
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected JSONArray doInBackground(String... urls) {

	ArrayList<NameValuePair> lbScores = new ArrayList<NameValuePair>();
	String result;
	j = null;
	try {
	    // uploadScore is only set on game over, so it will add to the
	    // database
	    if (uploadScore != null) {
		// For php to add score to the database
		lbScores.add(new BasicNameValuePair("score", String
			.valueOf(uploadScore.getScore())));
		lbScores.add(new BasicNameValuePair("name", uploadScore
			.getName()));

		HttpClient httpClient = new DefaultHttpClient();
		// Making call to webserver to insert into database
		HttpPost httpPost = new HttpPost(
			"http://brockcoscbrickbreakerleaderboard.web44.net/db_insert.php");
		httpPost.setEntity(new UrlEncodedFormEntity(lbScores));
		httpClient.execute(httpPost);
	    }
	    HttpClient httpClient = new DefaultHttpClient();
	    // Making call to webserver for table fo scores
	    HttpPost httpPost = new HttpPost(
		    "http://brockcoscbrickbreakerleaderboard.web44.net/db_config.php");
	    httpPost.setEntity(new UrlEncodedFormEntity(lbScores));
	    HttpResponse response = httpClient.execute(httpPost);
	    HttpEntity entity = response.getEntity();
	    InputStream is = entity.getContent();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    is, "iso-8859-1"), 8);
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    // This is just really for debugging, shows what the
	    // reponse to the server is, could've been used for
	    // happiness messages to user
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	    is.close();
	    result = sb.toString();

	    j = new JSONArray(result);

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return j;
    }

    /*
     * This method is called when the network calls are completed. JSONArray
     * result is the list of scores retrieved from the database.
     * 
     * This method buildes a local list of scores, and they get feeded back into
     * the main activity.
     * 
     * This is required to allow UI to continued while this task gets excecuted.
     * 
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    protected void onPostExecute(JSONArray result) {
	ArrayList<Score> scores = new ArrayList<Score>();

	try {
	    // Building up the list of scores;
	    for (int i = 0; i < result.length(); i++) {
		JSONObject o = result.getJSONObject(i);
		Score s = new Score();
		s.setName(o.get("name").toString());
		s.setScore(o.getInt("score"));
		scores.add(s);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// Returning the list of scores
	Message msg = new Message();
	msg.obj = scores;
	handler.sendMessage(msg);

    }
}
