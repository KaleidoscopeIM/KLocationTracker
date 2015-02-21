package com.kaleidoscope.klocationtracker;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;

public class KTrackerMainActivity extends ActionBarActivity {
	private Intent intent;
	private PendingIntent pintentIntent;
	private AlarmManager alarmManager;
	private Calendar cal;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context=KTrackerMainActivity.this;
		intent=new Intent(context, KTrackerServices.class);
		pintentIntent=PendingIntent.getService(context, 0, intent, 0); 
		alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		cal=Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		KTrackerConfiguration.androidDeviceID=Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		
		createAlarm();	
	}
	private void createAlarm() {
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), KTrackerConfiguration.ALARM_INTERVAL, pintentIntent);
	}
	//it will cancel a repeating task 
	private void cancelAlarm()
	{
		alarmManager.cancel(pintentIntent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}