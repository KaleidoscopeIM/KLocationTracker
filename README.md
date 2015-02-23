# KLocationTracker
KLocationTracker is a background service capable of tracking device location.
KLocationTracker is a background location tracker service which is isolated from the application and run in background. This background service will capture the device location at a specific time interval and it will upload the location meta-data on server encrypted with AES-256bit ECB encryption algorithm. The service has one main thread which will capture the current location and location changes. It will create a new child threads every time the data need to be upload on server or the data need to be encrypted. These child thread will perform their task and terminate. As there may be many threads so all the necessary methods are synchronised to avoid dead-lock condition.

This service will continue to look for a location change. If there is a location change of device the service will immediately record the new location data and send on server.
The service is configure to work in all situation for eg.:

1.	The service will be working if main app is in foreground.

2.	The service will work if app is in background.


3.	The service will continue to work if the app is killed by user.

4.	The service will work even if the device has restarted.  



A event is triggered every time device restarts which will start the service again.
The service is designed with many feature for easy integration, development and testing purpose for eg the service can write location specific logs in a file with current time which is configured in configuration class. This file can be used to know the flow of data at a specific time, testing and development.

This service is completely configurable by one of its class "KTrackerConfiguration.java" which has configuration related settings.
KTrackerConfiguration Class Features:

1.	captureDeviecMatrix=true/false
It will fetch device related information. This task will be executed only once.

2.	debugMode=true/false
If it is true than service will create a file if it not exists at specified location and specified name in specified configuration class, otherwise it will open the file.

3.	FileName="saini.txt"
This file will store location specific service logs.

4.	ExternalStorageDir=Environment.getExternalStorageDirectory();
Path/directory for the log file.

5.	deleteFileOnLaunch=true/false
If it is true than service will delete the log file on launch of the service.

6.	ALARM_INTERVAL=1000 * 60 * 1;
The time interval for the service to be triggered for location update in sec * min * hour.

7.	INTERVAL=1000*60;
FASTEST_INTERVAL=1000*30;
Location update intervals.

8.	doEncryption=true/false;
If it is true than service will encrypt the location data send on server.

9.	doUpload=true/false;
If it is true than only service will upload the location data on server.

10.	uploadURL="http://192.168.0.110/android_upload_folder/upload.php";
It should be a working http apache server or other with script running on it to accept incoming location data.

11.	secretKey="key"
A secret key for AES Encryption.

Thanks & Regards,
Gautam Saini (ASE)
+91-8374558862

