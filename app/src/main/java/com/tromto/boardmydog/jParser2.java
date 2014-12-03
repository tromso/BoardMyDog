package com.tromto.boardmydog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class jParser2 {
	
	static InputStream is = null;
	static JSONObject jObject = null;
	static String json = "";
	
	public jParser2(){
		
	}
	
	public JSONObject makeHttpRequest(String url, List<NameValuePair> params){
		
	try {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		is = httpEntity.getContent();
		
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
	try {
		BufferedReader reader = new BufferedReader (new InputStreamReader (is, "iso-8859-1"), 8);
		
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine())!=null){
			sb.append(line+"\n");
		}
		is.close();
		json = sb.toString();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
	}	
    try {
			jObject = new JSONObject(json);
	} catch (JSONException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	} 	
	
		return jObject;
	}
	
	

	
	
	
	

}
