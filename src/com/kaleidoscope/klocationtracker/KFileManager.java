package com.kaleidoscope.klocationtracker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.util.Log;

public class KFileManager extends Activity{
	 
	private static File file;
	public KFileManager() { 
		file=new File(KTrackerConfiguration.ExternalStorageDir,KTrackerConfiguration.FileName);	
	}	
	public synchronized void writeLocation(String str)
	{
		try {
			if(KTrackerConfiguration.debugMode)
			{	
	            if (!isFileExists()) {
	                file.createNewFile(); 
	                Log.d(KTrackerConfiguration.TAG, "create file at :"+file.getAbsolutePath());
	            } 
	           
	           // Log.d(KTrackerConfiguration.TAG, "file already at"+file.getAbsolutePath());
	            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(getLocationTime()+str+"\n");
	            bw.flush();
	            bw.close();
	             
			}else
			{
				if(isFileExists())
					file.delete();
			}
            //Log.d(KTrackerConfiguration.TAG,"done");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(KTrackerConfiguration.TAG, "error in writing in file");
        }
	}
	public String getFilePath()
	{
		return KTrackerConfiguration.ExternalStorageDir+File.separator+KTrackerConfiguration.FileName;
	}
	
	public Boolean isFileExists()
	{
		return file.exists();
	}
	public String getLocationTime()
	{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal=Calendar.getInstance();
		Date date=cal.getTime();
		return formatter.format(date)+" :";
	}
}
