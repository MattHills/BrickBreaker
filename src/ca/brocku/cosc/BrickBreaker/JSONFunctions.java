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
 */
/**
 * @author Dan
 *
 */
public class JSONFunctions extends AsyncTask<String, String, JSONArray> {
	    private Score uploadScore;
	    private JSONArray j;
	    private Handler handler;

    /**
     * 
     * @param handler
     */
    public void setHandler(Handler handler) {
    	this.handler = handler;
    }

    /**
     * 
     * @param uploadScore
     */
    public void setUploadScore(Score uploadScore) {
    	this.uploadScore = uploadScore;
    }

    /* (non-Javadoc)
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
		    if (uploadScore != null) {
				lbScores.add(new BasicNameValuePair("score", String
					.valueOf(uploadScore.getScore())));
				lbScores.add(new BasicNameValuePair("name", uploadScore
					.getName()));
		
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
					"http://brockcoscbrickbreakerleaderboard.web44.net/db_insert.php");
				httpPost.setEntity(new UrlEncodedFormEntity(lbScores));
				HttpResponse response = httpClient.execute(httpPost);
		    }
		    
		    HttpClient httpClient = new DefaultHttpClient();
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

    /* (non-Javadoc)
     * 
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    protected void onPostExecute(JSONArray result) {
		ArrayList<Score> scores = new ArrayList<Score>();
		
		try {
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
		
		Message msg = new Message();
		msg.obj = scores;
		handler.sendMessage(msg);

    }
}
