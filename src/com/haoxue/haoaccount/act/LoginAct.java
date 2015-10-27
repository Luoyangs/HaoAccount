package com.haoxue.haoaccount.act;

import java.util.Timer;
import java.util.TimerTask;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.MSG;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.RexUtil;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.SpotsDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

/**
 * 说明：登录
 * 作者：Luoyangs
 * 时间：2015-10-23
 */
@ContentView(R.layout.act_login_layout)
public class LoginAct extends Activity {

	@ViewInject(R.id.username)
	private EditText etName;
	@ViewInject(R.id.password)
	private EditText etPass;
	private SpotsDialog dialog;
	private SQLiteDatabase database;
	private int id = -1;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG.LOAD_OK:
				dialog.dismiss();
				ToastUtil.showShort(LoginAct.this, "登陆成功~");
				ShareDataHelper.getInstance(LoginAct.this).saveUser("userId", String.valueOf(msg.arg1));
				ShareDataHelper.getInstance(LoginAct.this).saveUser("login", String.valueOf(msg.obj));
				startActivity(new Intent(LoginAct.this,MainAct.class));
				finishPage();
				break;
			case MSG.LOAD_ERROR:
				dialog.dismiss();
				ToastUtil.showShort(LoginAct.this, "登陆失败，请检查你的填写~");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		dialog = SpotsDialog.createDialog(this);
	}
	
	@OnClick({R.id.btnlogin,R.id.btnregister})
	public void onLogin(View view){
		if (view.getId() == R.id.btnlogin) {
			login();
		}else if(view.getId() == R.id.btnregister){
			startActivity(new Intent(LoginAct.this,RegisterAct.class));
			this.finishPage();
		}
	}
	
	private void login(){
		final String user = etName.getText().toString().trim();
		final String pass = etPass.getText().toString().trim();
		if (user == null || user.length() == 0) {
			ToastUtil.showShort(LoginAct.this, "请输入账号~");
			etName.setFocusable(true);
			etName.setFocusableInTouchMode(true);
			etName.requestFocus();
			return;
		}
		if (pass == null || pass.length() == 0) {
			ToastUtil.showShort(LoginAct.this, "请输入密码~");
			etPass.setFocusable(true);
			etPass.setFocusableInTouchMode(true);
			etPass.requestFocus();
			return;
		}
		dialog.show();
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (RexUtil.isEmail(user)) {
					//邮箱登陆
					Cursor cursor = database.rawQuery(Constant.DB.LOG_USER_EMAIL, new String[]{user,pass});
					if (cursor.getCount() > 0) {
						//记录
						while (cursor.moveToNext()) {
							id = cursor.getInt(cursor.getColumnIndex("id"));
						}
						Message msg = Message.obtain();
						msg.what = MSG.LOAD_OK;
						msg.obj = user;
						msg.arg1 = id;
						handler.sendMessage(msg);
					}else{
						handler.sendEmptyMessage(MSG.LOAD_ERROR);
					}
				}else{
					//手机号登陆
					Cursor cursor = database.rawQuery(Constant.DB.LOG_USER_PHONE, new String[]{user,pass});
					if (cursor.getCount() > 0) {
						//记录
						while (cursor.moveToNext()) {
							id = cursor.getInt(cursor.getColumnIndex("id"));
						}
						Message msg = Message.obtain();
						msg.what = MSG.LOAD_OK;
						msg.obj = user;
						msg.arg1 = id;
						handler.sendMessage(msg);
					}else{
						handler.sendEmptyMessage(MSG.LOAD_ERROR);
					}
				}

			}
		}, 3000);
	}
	
	@OnClick({R.id.login_qq,R.id.login_weixin,R.id.login_weibo})
	public void onOtherLogin(View view){
		switch (view.getId()) {
		case R.id.login_qq:

			break;
		case R.id.login_weixin:

			break;
		case R.id.login_weibo:

			break;
		}
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
}
