package com.kaleidoscope.ktrackerbroadcast;

import com.kaleidoscope.klocationtracker.KTrackerServices;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

public class KTrackerBroadcastLocation extends Activity{
	public static String action="com.kaleidoscope.klocationtracker.action.msgReceiver";
	private Handler handler;
	String lData="";
	Intent intent;
	KTrackerServices serviceActivity;
	public KTrackerBroadcastLocation() {
		// TODO Auto-generated constructor stub
			intent=new Intent();
			handler=new Handler();
	}
	public void broadcastMessage(String locationData,KTrackerServices kTrackerServices) {
			
			handler.postDelayed(sendMSG,1000);
			lData=locationData;
			serviceActivity=kTrackerServices;
	}
	public Runnable sendMSG=new Runnable() {
		
		@Override
		public void run() {
			intent.setAction(action);
			intent.putExtra("location", lData);
			serviceActivity.sendBroadcast(intent);
			
		}
	};
	

}
