package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.SpotsDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		dialog = SpotsDialog.createDialog(this);
	}
	
	@OnClick({R.id.btnlogin,R.id.btnregister})
	public void onLogin(View view){
		if (view.getId() == R.id.btnlogin) {
			ToastUtil.showShort(this, "登录");
			login();
		}else if(view.getId() == R.id.btnregister){
			startActivity(new Intent(LoginAct.this,RegisterAct.class));
			this.finishPage();
		}
	}
	
	private void login(){
		dialog.show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		dialog.dismiss();
		startActivity(new Intent(LoginAct.this,UserInfoAct.class));
		this.finish();
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
