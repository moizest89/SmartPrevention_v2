package com.example.smartprevention;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity {

    private GoogleMap googleMap;
    static final LatLng ESA = new LatLng(-89.63, 13.87);
    private MapManager mapZones = new MapManager();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
 
        try {
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
 
    public void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
 
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "No se pudo crear el mapa", Toast.LENGTH_SHORT).show();
            }
            else{
            	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ESA, 13));
            	
            	//Get safe zones
            	mapZones.setCustomEventListener(new OnCustomEventListener(){
					public void onEvent(){
						for(int i = 0; i < mapZones.elements.size(); i++){
							Bundle item = mapZones.elements.get(i);
							
							MarkerOptions marker = new MarkerOptions().position(new LatLng(item.getDouble("latitude"), item.getDouble("longitude"))).title(item.getString("name"));
							googleMap.addMarker(marker);
						}
					}
				});
				
            	mapZones.getSafeZones();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
