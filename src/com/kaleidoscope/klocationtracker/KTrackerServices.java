package com.kaleidoscope.klocationtracker;
import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class KTrackerServices extends Service implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{

	protected GoogleApiClient mGoogleApiClient;
	protected LocationRequest mLocationRequest;
	protected Location mCurrentLocation=null;
	protected KFileManager fileManager=null;
	protected LocationManager locationManager;
	public String broadcastAction="com.kaleidoscope.klocationtracker.action.msgReceiver";
	protected Handler broadcastHandler;
	protected Intent broadcastIntent;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	} 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(KTrackerConfiguration.deleteFileOnLaunch)
		{
			File file=new File(KTrackerConfiguration.ExternalStorageDir,KTrackerConfiguration.FileName);	
			if(file.exists())
			{
				file.delete();
				Log.d(KTrackerConfiguration.TAG, "file deleted");
			}
		} 
		fileManager=new KFileManager();
		broadcastIntent=new Intent();
		broadcastHandler=new Handler();
		fileManager.writeLocation("Services created..");
		locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		fileManager.writeLocation(KTrackerConfiguration.getConfiguration());
		buildGoogleApiClient();
		setLocationUpdates();
		if(KTrackerConfiguration.deviceMatrix=="")
		{
			if(KTrackerConfiguration.captureDeviecMatrix)
			{
				fetchDeviceData();
			}
		}
		if(mGoogleApiClient!=null)
			mGoogleApiClient.connect();
		else
			fileManager.writeLocation("Google API Client is null");	
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		fileManager.writeLocation("calling services..");
		if(isGeoLocationAvailable())
		{
			if(mGoogleApiClient.isConnected())
			{
				fileManager.writeLocation("Connected to Google API Client..");
				LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
				
			}else
			{
				fileManager.writeLocation("NOT connected to Google API Client Services..");
				if(mGoogleApiClient!=null)
				{
					fileManager.writeLocation("Trying to Connect Google API Client Services..");
					mGoogleApiClient.connect();
				}
			} 
		}else
		{
			fileManager.writeLocation("Network | GPS location not available");
		}	
		return super.onStartCommand(intent, flags, startId);	
	}
	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		fileManager.writeLocation("Connection Suspended..Trying to reConnect..");
		mGoogleApiClient.connect(); 
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		fileManager.writeLocation("Successfully Connected to Google Client API Services..");
		
		if(mCurrentLocation==null)
		{
			mCurrentLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
			if(mCurrentLocation!=null)
			{
				
				fileManager.writeLocation("Got Last Location from FusedLocationAPI..");
				fileManager.writeLocation("Latitude :"+mCurrentLocation.getLatitude());
				fileManager.writeLocation("Longitude :"+mCurrentLocation.getLongitude());
				fileManager.writeLocation("Accuracy :"+mCurrentLocation.getAccuracy());
				fileManager.writeLocation("Altitude :"+mCurrentLocation.getAltitude());
				fileManager.writeLocation("Provider :"+mCurrentLocation.getProvider());
				if(KTrackerConfiguration.doUpload)
				{ 
					JSONObject jsonData=new JSONObject();
					try {
						jsonData.put("DeviceID:", KTrackerConfiguration.androidDeviceID);
						jsonData.put("Latitude", mCurrentLocation.getLatitude());
						jsonData.put("Longitude", mCurrentLocation.getLongitude());
						jsonData.put("Accuracy :", mCurrentLocation.getAccuracy());
						jsonData.put("Provider :",mCurrentLocation.getProvider());
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						fileManager.writeLocation("Error in json creation :"+e.getLocalizedMessage());
					}
					new KUploadServices().execute(jsonData);
					Log.d(KTrackerConfiguration.TAG, "in ktrackerservices :"+jsonData.toString());
				}
				if(KTrackerConfiguration.updateUI)
				{
					String localString;
					localString="Last Location from FusedLocationAPI..\n"+"Latitude :"+mCurrentLocation.getLatitude()+"\n"+"Longitude :"+mCurrentLocation.getLongitude()+"\n"+"Provider :"+mCurrentLocation.getProvider();
					broadcastLocationData(localString);		
				}
			}
		}	
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		fileManager.writeLocation("Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());	
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		fileManager.writeLocation("Tracking Location..");
		mCurrentLocation=location;
		fileManager.writeLocation("Latitude :"+mCurrentLocation.getLatitude());
		fileManager.writeLocation("Longitude :"+mCurrentLocation.getLongitude());
		fileManager.writeLocation("Accuracy :"+mCurrentLocation.getAccuracy());
		fileManager.writeLocation("Altitude :"+mCurrentLocation.getAltitude());
		fileManager.writeLocation("Provider :"+mCurrentLocation.getProvider());
		if(KTrackerConfiguration.doUpload)
		{ 
			JSONObject jsonData=new JSONObject();
			try {
				jsonData.put("DeviceID:", KTrackerConfiguration.androidDeviceID);
				jsonData.put("Latitude", mCurrentLocation.getLatitude());
				jsonData.put("Longitude", mCurrentLocation.getLongitude());
				jsonData.put("Accuracy :", mCurrentLocation.getAccuracy());
				jsonData.put("Provider :",mCurrentLocation.getProvider());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				fileManager.writeLocation("Error in json creation :"+e.getLocalizedMessage());
			}
			new KUploadServices().execute(jsonData);
		}
		if(KTrackerConfiguration.updateUI)
		{
			String localString="Tracking Location..\n"+"Latitude :"+ mCurrentLocation.getLatitude()+"\n"+"Longitude"+mCurrentLocation.getLongitude()+"\n"+"Provider :"+mCurrentLocation.getProvider();
			broadcastLocationData(localString);
		}
		LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
	}

	private void fetchDeviceData() {  
				fileManager.writeLocation("Fetching device data..");
				JSONObject jsonData=new JSONObject();
				try{
					jsonData.put("DeviceID:", KTrackerConfiguration.androidDeviceID);
					jsonData.put("Android Version",android.os.Build.VERSION.RELEASE );
					jsonData.put("manufacturer",Build.MANUFACTURER);
					jsonData.put("Model", Build.MODEL);
					jsonData.put("SDK Version",Build.VERSION.SDK_INT);
					jsonData.put("SDK String", Build.VERSION.SDK);
					jsonData.put("Product", Build.PRODUCT);
					jsonData.put("Brand", Build.BRAND);
					jsonData.put("Hardware",Build.HARDWARE );
					jsonData.put("Host",Build.HOST );
					jsonData.put("Codename",Build.VERSION.CODENAME);
					jsonData.put("Release",Build.VERSION.RELEASE);
					jsonData.put("Fingerprint", Build.FINGERPRINT);	
				}catch(JSONException e)
				{
					fileManager.writeLocation("Exception found in writing JSON :"+e.getLocalizedMessage());
				} 
				KTrackerConfiguration.deviceMatrix=jsonData.toString();
				fileManager.writeLocation("Device Info:"+"\n"+jsonData.toString());
				String localString="";
				localString+="DeviceID :" +KTrackerConfiguration.androidDeviceID+"\n"+
				"Android Version :"+android.os.Build.VERSION.RELEASE+"\n"+"manufacturer :"+Build.MANUFACTURER
				+"\n"+"Model :"+ Build.MODEL;
				broadcastLocationData(localString);
				if(KTrackerConfiguration.doUpload)
				{
					new KUploadServices().execute(jsonData);
				}
	}
	public void broadcastLocationData(String localString) {
		/*KTrackerBroadcastLocation b=new KTrackerBroadcastLocation();
		b.broadcastMessage(localString, this);*/
		broadcastIntent.setAction(broadcastAction);
		broadcastIntent.putExtra("locationData", localString);
		broadcastHandler.postDelayed(broadcastMessage,1000);
	}
	public Runnable broadcastMessage=new Runnable() {
		
		@Override
		public void run() {
			sendBroadcast(broadcastIntent);
		}
	};
	public boolean isGeoLocationAvailable() {
		KTrackerConfiguration.isGPSAvailable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//fileManager.writeLocation("GPS Location Available :"+KTrackerConfiguration.isGPSAvailable);
		KTrackerConfiguration.isNetworkAvailable=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		//fileManager.writeLocation("Network Location Available :"+KTrackerConfiguration.isNetworkAvailable);
		if(KTrackerConfiguration.isGPSAvailable && KTrackerConfiguration.isNetworkAvailable)
			return true;
		else
			return false;
	}
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(LocationServices.API)
		.build();
		}
	private void setLocationUpdates() {
		mLocationRequest=LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(KTrackerConfiguration.INTERVAL);
		mLocationRequest.setFastestInterval(KTrackerConfiguration.FASTEST_INTERVAL);
	}
    
}
