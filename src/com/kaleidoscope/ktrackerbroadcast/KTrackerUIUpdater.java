package com.kaleidoscope.ktrackerbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.kaleidoscope.klocationtracker.KTrackerConfiguration;
import com.kaleidoscope.klocationtracker.KTrackerMainActivity;

public class KTrackerUIUpdater extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String data=intent.getStringExtra("location");
		Log.d(KTrackerConfiguration.TAG, "Data reveived in rceiver :"+data);
		
		KTrackerMainActivity.tv.setText(data);
	}
	
	
		/*BroadcastReceiver receiver=new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String data=intent.getStringExtra("location");
				Log.d(KTrackerConfiguration.TAG, "Data reveived in rceiver :"+data);
				TextView tv=(TextView)act.findViewById(R.id.locationData);
				tv.setText(data);
			}
		};*/
		
	
}
