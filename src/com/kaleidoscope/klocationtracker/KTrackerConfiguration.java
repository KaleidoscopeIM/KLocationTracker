package com.kaleidoscope.klocationtracker;

import java.io.File;
import android.os.Environment;

public class KTrackerConfiguration {
	public static String TAG="KLocationTracker";
	public static String deviceMatrix="";
	public static boolean captureDeviecMatrix=true;
	public static String androidDeviceID="";
	//it will send broadcast messages to update the UI of the application
	public static boolean updateUI=true;
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
	public static String uploadURL="http://172.20.10.2/android_upload_folder/upload.php";
	public static String secretKey="Secret_Key";
	public static int encryptionBit=32; //32 bytes = 256 bit
	public static String initializationVector = "4f339d11157e7388";//CryptLib.generateRandomIV(16); //16 bytes = 128 bit
	
}
