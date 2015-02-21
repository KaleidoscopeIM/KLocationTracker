# KLocationTracker
KLocationTracker is a background service capable of tracking device location.\n
It can send data on server and it can encrypt it using AES-128 bit ECB algorithm. 
This service is completely configurable by one of its class "KTrackerConfiguration".

Features:
1. It will track device location at specific time inteval confiured in configuration class.
2. It will track device location as it will change.
3. It can write location specific logs in a file.
4. It can send location data on server.
5. It can encrypt the data send on server using AES-128 bit ECB.

This service has one main service thread which will track location and it will create new thread when the location data need to be upload on server.

KTrackerConfiguration Class Features:
public static String TAG="KLocationTracker";
1.captureDeviecMatrix=true/false
  It will fetch device information.This task will be executed only once.
2.debugMode=true/false
  If it is true than service will create a file at specified location and specified name in configuration class if it   not exists otherwise it will open the file.
3.FileName="saini.txt"
  File name location specific logs.
4.ExternalStorageDir=Environment.getExternalStorageDirectory();
  Path for the file
5.deleteFileOnLaunch=true/false
  If it is ture than service will delete the log file on launch of the service.
6.ALARM_INTERVAL=1000 * 60 * 1;
  If will specify the time interval for the service to be trigger for location update in sec * min * hour
7.INTERVAL=1000*60;
	FASTEST_INTERVAL=1000*30;
  Location update intervals
8.doEncryption=true/false;
  If it is true than service will encrypt the data send on server.
9.doUpload=true/false;
  If it is true than service will upload the location data on server.
10.uploadURL="http://192.168.0.110/android_upload_folder/upload.php";
  A working http apache server or other with script to accept location data is mendatory if doUpload is true 
