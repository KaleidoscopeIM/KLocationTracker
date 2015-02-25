package com.kaleidoscope.klocationtracker;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.widget.TextView;

public class KTrackerMainActivity extends Activity{
	private Intent intent;
	private PendingIntent pintentIntent;
	private AlarmManager alarmManager;
	private Calendar cal;
	
	public static TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Context context=KTrackerMainActivity.this;
		intent=new Intent(context, KTrackerServices.class);
		pintentIntent=PendingIntent.getService(context, 0, intent, 0); 
		alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		KTrackerConfiguration.androidDeviceID=Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		createAlarm();	
		
		tv=(TextView)findViewById(R.id.locationData);
		//tv.setText("Hiiii");
		
	}
	private void createAlarm() {
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), KTrackerConfiguration.ALARM_INTERVAL, pintentIntent);
	}
	
	/*//it will cancel a repeating task 
	private void cancelAlarm()
	{
		alarmManager.cancel(pintentIntent);
	}*/
	
}