package com.kaleidoscope.ktrackerbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kaleidoscope.klocationtracker.KTrackerConfiguration;
import com.kaleidoscope.klocationtracker.KTrackerMainActivity;

public class KTrackerUIUpdater extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction()==Intent.ACTION_BOOT_COMPLETED)
		{
			
		}
	}
}