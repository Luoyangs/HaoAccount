package com.haoxue.haoaccount.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 说明:数据
 * 作者:Luoyangs
 * 时间:2015-9-25
 */
@SuppressLint("SimpleDateFormat")
public final class ShareDataHelper {

	/**程序数据（第一次进入）*/
	private SharedPreferences appshare;
	/**程序设置*/
	private SharedPreferences setshare;
	/**用户设置*/
	private SharedPreferences usershare;

	private static ShareDataHelper instance = null;
	
	public static ShareDataHelper getInstance(Context context){
		if (instance == null) {
			instance = new ShareDataHelper(context);
		}
		return instance;
	}
	
	private ShareDataHelper(Context context){
		//初始化操作
		appshare = context.getSharedPreferences("appshare", Context.MODE_PRIVATE);
		setshare = context.getSharedPreferences("setshare", Context.MODE_PRIVATE);
		usershare = context.getSharedPreferences("usershare", Context.MODE_PRIVATE);
	}
	
	/**保存登录信息（是否第一次初始化程序）*/
	public void saveLoadingInfo(boolean isFirstIn){
		Editor editor = appshare.edit();
    	editor.putBoolean(Constant.IS_FIRST_LOGIN, isFirstIn);
		editor.commit();
	}
	
	/**获取登录信息（是否第一次初始化程序,默认为true）*/
	public boolean getLoadingInfo(){
		return appshare.getBoolean(Constant.IS_FIRST_LOGIN, true);
	}
	
	/**保存登录密码信息*/
	public void savePassword(String key,String value){
		Editor editor = appshare.edit();
    	editor.putString(key, value);
		editor.commit();
	}
	
	/**获取登录密码*/
	public String getPassword(String key){
		return appshare.getString(key, "");
	}
	
	/**保存刷新加载信息*/
	public void savePullToRefreshTime(String key,String value){
		Editor editor = appshare.edit();
    	editor.putString(key, value);
		editor.commit();
	}
	
	/**获取刷新加载信息*/
	public String getPullToRefreshTime(String key){
		return appshare.getString(key, new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
	}
	
	/**保存设置信息*/
	public void saveSetParams(String key,String value){
		Editor editor = setshare.edit();
    	editor.putString(key, value);
		editor.commit();
	}
	
	/**保存密码设置*/
	public void savePassSet(String key, boolean value){
		Editor editor = setshare.edit();
    	editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**获取密码设置*/
	public boolean getPassSet(String key){
		return setshare.getBoolean(key, true);
	}
	
	/**获取设置信息*/
	public String getSetStringParam(String key){
		return setshare.getString(key, "");
	}
	
	/**保存用户信息*/
	public void saveUser(String key, String value){
		Editor editor = usershare.edit();
    	editor.putString(key, value);
		editor.commit();
	}
	
	/**获取用户信息*/
	public String getUser(String key){
		return usershare.getString(key, "");
	}
	
	/**判断是否存在用户登录*/
	public boolean hasLogin(){
		return !usershare.getString("userId", "").equals("");
	}
}
