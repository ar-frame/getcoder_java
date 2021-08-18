package com.coopcoder.getcoder;

import java.util.Random;

public class Config {
	String SERVER_ADDRESS;
	String AUTH_SERVER_OPKEY;
	String USER_ACCESS_TOKEN;
	String SERVER_RESPONSE_TAG;
	String USER_AUTH_KEY;
	
	public Config(String SERVER_ADDRESS, String AUTH_SERVER_OPKEY) {
		this.SERVER_ADDRESS = SERVER_ADDRESS;
		this.AUTH_SERVER_OPKEY = AUTH_SERVER_OPKEY;
		this.USER_ACCESS_TOKEN = getRandomString(10);
		this.SERVER_RESPONSE_TAG = "___SERVICE_STD_OUT_SEP___";
	}
	
	public void setUserAuthKey(String key)
	{
		USER_AUTH_KEY = key;
	}

	public static String getRandomString(int length){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++){
			int number=random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
