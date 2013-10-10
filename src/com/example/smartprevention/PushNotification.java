package com.example.smartprevention;

import android.app.Activity;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class PushNotification {
	
	public PushNotification(Context context){
	    Parse.initialize(context, "E7c9LwTnI8NgF3QXtS8WdpQgqzpHYcKJCfcTacXy", "D1V5tfIKQHOuP54JfGnmiRxcb5Dn5rulIdsV5Acs"); 
	    
	    //Display activity
	    //PushService.subscribe(context, "Giants", activity_class);
	    PushService.setDefaultPushCallback(context, AlertActivity.class);
	    
	    ParseInstallation.getCurrentInstallation().saveInBackground();			    
	}

}

//Doc: https://parse.com/docs/push_guide#receiving/Android
