package com.kaleidoscope.klocationtracker;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import android.os.AsyncTask;

public class KTrackerUploadServices extends AsyncTask<JSONObject, Void, Void>{
	KFileManager fileManager=null;
	HttpClient httpClient;
	HttpPost httpPost;
	HttpResponse httpResponse;
	int statusCode;
	KTrackerAESEncryption aesEncryption;
	public KTrackerUploadServices() {
		// TODO Auto-generated constructor stub
		fileManager=new KFileManager();
		httpClient=new DefaultHttpClient();
		httpPost=new HttpPost(KTrackerConfiguration.uploadURL);
		aesEncryption=new KTrackerAESEncryption();
		 
	}
	@Override
	protected Void doInBackground(JSONObject... params) {
		try
		{
				fileManager.writeLocation("Uploading data to server..");
				for(JSONObject jObject:params)
				{
					String locationString;
					List<NameValuePair> postData=new ArrayList<NameValuePair>();
					if(KTrackerConfiguration.doEncryption)
						locationString=aesEncryption.encryptLocationData(jObject.toString());
					else
						locationString=jObject.toString();
					fileManager.writeLocation("location string :"+locationString);
					postData.add(new BasicNameValuePair("data", locationString));
					UrlEncodedFormEntity entity=new UrlEncodedFormEntity(postData);
					entity.setContentEncoding(HTTP.UTF_8);
					httpPost.setEntity(entity);
					httpResponse=httpClient.execute(httpPost);
					statusCode=httpResponse.getStatusLine().getStatusCode();
					if(statusCode==200)
					{
						fileManager.writeLocation("Data uploaded:"+"\n"+jObject.toString());
					}else{
						fileManager.writeLocation("Upload error..server respond with code :"+statusCode);
					}
				}	
		}catch(Exception e)
		{
			fileManager.writeLocation("failed to upload error:"+e);
		}
		fileManager.writeLocation("upload finished..");
		return null;
	}
}