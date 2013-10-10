package com.example.smartprevention;

import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.util.Log;

public class UserPing  extends WebServiceManager {
	
	Context context;
	
	public void ping(Double longitude, Double latitude, int user_id, Context tmpContext){
    	JSONObject json = new JSONObject();
		JSONObject position = new JSONObject();
		String getData = "";
		context = tmpContext;
		
    	try{
			position.put("longitude", longitude);
			position.put("latitude", latitude);
			json.put("position", position);
			getData = json.getString("position");
            
        	postData = json;
        	operation = "login";
        	
        	if(isNetworkConnected()){
        		startConnection("api/users/parser_id/" + user_id + "/ping/" + getData, "POST");			
        	}
        	else{
        		SmsManager smsManager = SmsManager.getDefault();
        		smsManager.sendTextMessage("+14043342471", null, "{id:1,latitude:" + latitude.toString() + ", longitude:" + longitude.toString() + ",phone_number:73450852}", null, null);
        	}        	
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
    
    private boolean isNetworkConnected() {
    	return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}