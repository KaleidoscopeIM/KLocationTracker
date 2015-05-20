package com.kaleidoscope.ktrackerbroadcast;

import java.util.Calendar;

import com.kaleidoscope.klocationtracker.KFileManager;
import com.kaleidoscope.klocationtracker.KTrackerConfiguration;
import com.kaleidoscope.klocationtracker.KTrackerMainActivity;
import com.kaleidoscope.klocationtracker.KTrackerServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class KTrackerBroadcastServices extends BroadcastReceiver{

	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction()==Intent.ACTION_BOOT_COMPLETED) 
		{ 
			Log.d(KTrackerConfiguration.TAG, "event triggered :"+intent.getAction());
			AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Intent rebootIntent=new Intent(context, KTrackerServices.class);
			PendingIntent pint=PendingIntent.getService(context, 0, rebootIntent, 0);
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis() , KTrackerConfiguration.INTERVAL, pint);
			Intent i=new Intent(context, KTrackerServices.class);
			context.startService(i);
		}
		if(intent.getAction()==Intent.ACTION_POWER_CONNECTED)
		{
			// increase the location update speed
			
			
		}if(intent.getAction()=="com.kaleidoscope.klocationtracker.action.msgReceiver")
		{
			String data=intent.getStringExtra("locationData");
			Log.d(KTrackerConfiguration.TAG, "Data reveived in receiver :"+data);
			KTrackerMainActivity.tv.setText(data);
		}
		if(intent.getAction()==Intent.ACTION_POWER_DISCONNECTED)
		{
			
		}
		if(intent.getAction()==Intent.ACTION_AIRPLANE_MODE_CHANGED)
		{
			//stop location update
			
		}
		if(intent.getAction()==Intent.ACTION_BATTERY_LOW)
		{
			//slow down location update
		}
		if(intent.getAction()==Intent.ACTION_DEVICE_STORAGE_LOW)
		{
			//stop log file write
		}
		if(intent.getAction()==Intent.ACTION_SCREEN_OFF)
		{
			// thinking idea
		}
		if(intent.getAction()==Intent.ACTION_SCREEN_ON)
		{
			//thinking idea
		}
		if(intent.getAction()==Intent.ACTION_PROVIDER_CHANGED)
		{
			//not that provider has changes
		}
	}

}
