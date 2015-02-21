package com.kaleidoscope.klocationtracker;

import java.io.File;
import android.os.Environment;

public class KTrackerConfiguration {
	public static String TAG="KLocationTracker";
	public static String deviceMatrix="";
	public static boolean captureDeviecMatrix=true;
	public static String androidDeviceID="";
	
	//check network!GPS Status
	public static boolean isGPSAvailable;
	public static boolean isNetworkAvailable;
	 
	//make it false if don't want to write in file
	public static boolean debugMode=true;
	public static String FileName="saini.txt";
	public static File ExternalStorageDir=Environment.getExternalStorageDirectory();
	public static boolean deleteFileOnLaunch=true;
	
	//service trigger interval in sec * min * hour;
	public static long ALARM_INTERVAL=1000 * 60 * 1;
	
	//Location update intervals
	public static final long INTERVAL=1000*60;
	public static final long FASTEST_INTERVAL=1000*30;
	
	//configuration related to encryption of location data
	public static boolean doEncryption=true;
	public static boolean doUpload=true;
	public static String uploadURL="http://192.168.0.110/android_upload_folder/upload.php";
	public static int UPLOAD_LOCATION_DATA=1;
	public static int UPLOAD_DEVICE_MATRIX=2;
}
