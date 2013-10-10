package com.example.smartprevention;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class WebServiceManager extends AsyncTask <String, Void, String> {
	
	protected String data_scope = new String();
	protected String operation = new String();	
	private String remoteAddress = "http://198.199.75.241:3000";
	protected String Content;
	protected String Error = null;
	protected String mode = new String();
	protected JSONObject postData;
	protected HttpResponse response;
	OnCustomEventListener mListener;
	
	public void setCustomEventListener(OnCustomEventListener eventListener) {
		mListener=eventListener;
	}
	
    protected void startConnection(String url, String selectedMode){
    	mode = selectedMode;
    	this.execute(remoteAddress + "/" + url);
    }
	
    protected void startConnection(String url, String selectedMode, JSONObject userPostData){
    	mode = selectedMode;
    	postData = userPostData;
    	this.execute(remoteAddress + "/" + url);
    }
    
    protected void onPreExecute() {       
    	//TODO
    }
	
    protected String doInBackground(String... urls) {
    	String value = new String();
    	
    	if(mode == "GET"){
    		value = readJSONFeed(urls[0]);
    	}
    	else if(mode == "POST"){
    		sendData(urls[0], postData);
    		value = "OK";
    	}
    	
    	return value;
    }
    
    protected void sendData(String url_path, JSONObject data){ 	
    	try{            
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);

            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost request = new HttpPost(url_path);
            request.addHeader("Accept","application/json");
            request.addHeader("Content-Type","application/json");
            request.setEntity(new ByteArrayEntity(data.toString().getBytes("UTF8")));
            response = client.execute(request);
            
            Log.i("SendData", response.toString());
    	}
    	catch(Exception ex){
    		Error = ex.getMessage();
    	}
    }
    
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }        
        return stringBuilder.toString();
    }
    
    protected void onPostExecute(String result) {
    	try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray items = new JSONArray(jsonObject.getString(data_scope));      		
    	}
    	catch (Exception e) {
    		Log.d("WebServiceManager", e.getLocalizedMessage());
    	}
    }
    
    public String getEvent(){
    	return operation;
    }

}
