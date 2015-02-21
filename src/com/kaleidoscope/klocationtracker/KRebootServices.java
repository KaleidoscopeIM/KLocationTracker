package com.kaleidoscope.klocationtracker;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KRebootServices extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		KFileManager fileManager=new KFileManager();
		fileManager.writeLocation("Device reboot complete..");
		AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent rebootIntent=new Intent(context, KTrackerServices.class);
		PendingIntent pint=PendingIntent.getService(context, 0, rebootIntent, 0);
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis() , KTrackerConfiguration.INTERVAL, pint);
		
		Intent i=new Intent(context, KTrackerServices.class);
		context.startService(i);
		fileManager.writeLocation("Services started after reboot..");
	}
	

}
