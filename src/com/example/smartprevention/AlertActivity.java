package com.example.smartprevention;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class AlertActivity extends Activity {
	
	LocationManager lm; 
	LocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);

		/*
		Button btn1 = (Button)findViewById(R.id.button1);
		Button btn2 = (Button)findViewById(R.id.button2);
		Button btn3 = (Button)findViewById(R.id.button3);
		
		btn1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				ping('save');
			}
		});
		
		btn2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				ping('sos');
			}
		});
		
		btn3.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				ping('panic');
			}
		});
		
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alert, menu);
		return true;
	}
	
	private void ping(String value){
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		double longitude = location.getLongitude();
		double latitude = location.getLatitude();		
		
		//SEND DATA
		UserPing pingManager = new UserPing();
		pingManager.ping(longitude, latitude, 1, getBaseContext());
		
		//EVENT => DISPLAY MAP
	}

}
