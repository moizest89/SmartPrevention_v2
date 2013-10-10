package com.example.smartprevention;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;

public class MapManager extends WebServiceManager {
	
	public JSONArray zones;
	public ArrayList<Bundle> elements;
	
	public void getSafeZones(){
    	operation = "getSafeZones";
    	startConnection("zones.json", "GET");		
	}
	
	public void getNearestSafeZones(Double longitude, Double latitude){
    	JSONObject json = new JSONObject();
		JSONObject position = new JSONObject();
		
		String getData = "";
		try{
			position.put("longitude", longitude);
			position.put("latitude", latitude);
			json.put("position", position);
			getData = json.getString("position");
		}
    	catch(Exception e){
    		Log.d("MapManager", e.getLocalizedMessage());
    	}
		
    	operation = "getSafeZonesOnDanger";
    	startConnection("zones.json?" + getData, "GET");	
	}
    
    protected void onPostExecute(String result) {
    	try {           
    		JSONArray tmpZones = new JSONArray(result);
    		elements = new ArrayList <Bundle>();
    		
    		for(int i = 0; i < tmpZones.length(); i++){
    			JSONObject zone = (JSONObject)tmpZones.get(i);
    			Bundle item = new Bundle();
    			
    			item.putString("name", zone.get("name").toString());
    			item.putDouble("latitude", (Double) zone.getJSONObject("position").get("latitude"));
    			item.putDouble("longitude", (Double) zone.getJSONObject("position").get("longitude"));
    		
    			elements.add(item);
    		}

        	mListener.onEvent();
    	}
    	catch (Exception e) {
    		Log.d("MapManager", e.getLocalizedMessage());
    	}
    }

}
