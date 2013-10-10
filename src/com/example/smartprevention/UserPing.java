package com.example.smartprevention;

import org.json.JSONObject;

import android.util.Log;

public class UserPing  extends WebServiceManager {
	
	public void ping(Double longitude, Double latitude, int user_id){
    	JSONObject json = new JSONObject();
		JSONObject position = new JSONObject();
		String getData = "";
		
    	try{
			position.put("longitude", longitude);
			position.put("latitude", latitude);
			json.put("position", position);
			getData = json.getString("position");
            
        	postData = json;
        	operation = "login";
        	startConnection("api/users/parser_id/" + user_id + "/ping/" + getData, "POST");			
    	}
    	catch(Exception ex){
    		//TODO
    	}			
	}
    
    protected void onPostExecute(String result) {
    	try {           
        	mListener.onEvent();
    	}
    	catch (Exception e) {
    		Log.d("UserPing", e.getLocalizedMessage());
    	}
    }

}