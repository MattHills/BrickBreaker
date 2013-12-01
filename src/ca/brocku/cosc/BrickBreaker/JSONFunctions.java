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

import android.os.AsyncTask;

public class JSONFunctions extends AsyncTask<String, String, JSONArray> {
    Score uploadScore;

    @Override
    protected JSONArray doInBackground(String... urls) {

	ArrayList<NameValuePair> lbScores = new ArrayList<NameValuePair>();
	String result;
	JSONArray j = null;
	try {
	    if (uploadScore == null) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
			"http://brockcoscbrickbreakerleaderboard.web44.net/db_config.php");
		httpPost.setEntity(new UrlEncodedFormEntity(lbScores));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		BufferedReader reader = new BufferedReader(
			new InputStreamReader(is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    sb.append(line + "\n");
		}
		is.close();
		result = sb.toString();

		j = new JSONArray(result);
		// for (int i = 0; i < j.length(); i++) {
		// JSONObject o = j.getJSONObject(i);
		// Score s = new Score();
		// s.setName(o.get("name").toString());
		// s.setScore(o.getInt("score"));
		// }
	    } else {
		lbScores.add(new BasicNameValuePair("score", String
			.valueOf(uploadScore.getScore())));
		lbScores.add(new BasicNameValuePair("name", uploadScore
			.getName()));

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
			"http://brockcoscbrickbreakerleaderboard.web44.net/db_insert.php");
		httpPost.setEntity(new UrlEncodedFormEntity(lbScores));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		BufferedReader reader = new BufferedReader(
			new InputStreamReader(is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    sb.append(line + "\n");
		}
		is.close();
		result = sb.toString();

		j = new JSONArray(result);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return j;

    }
}
