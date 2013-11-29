package ca.brocku.cosc.BrickBreaker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONFunctions extends AsyncTask<String, String, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... urls) {
	InputStream is = null;
	String result = "";
	JSONObject jArray = null;

	try {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httpost = new HttpPost(urls[0]);
	    HttpResponse response = httpclient.execute(httpost);
	    HttpEntity entity = response.getEntity();
	    is = entity.getContent();
	} catch (Exception e) {
	    Log.e("log_tag", "Error in connection " + e.toString());
	}

	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    is, "iso-8859-1"), 8);
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	    is.close();
	    result = sb.toString();
	} catch (Exception e) {
	    Log.e("log_tag", "Error converting result " + e.toString());
	}
	try {

	    jArray = new JSONObject(result);
	} catch (JSONException e) {
	    Log.e("log_tag", "Error parsing data " + e.toString());
	}
	return jArray;
    }
}
