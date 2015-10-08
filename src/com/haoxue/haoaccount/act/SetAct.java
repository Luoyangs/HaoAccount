package com.haoxue.haoaccount.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.haoxue.haoaccount.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 说明:设置
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
@ContentView(R.layout.act_set_layout)
public class SetAct extends Activity implements OnCheckedChangeListener{

	@ViewInject(R.id.tb_push)
	private ToggleButton tb_push;
	@ViewInject(R.id.tb_pass)
	private ToggleButton tb_pass;
	@ViewInject(R.id.tb_gps)
	private ToggleButton tb_gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		boolean showPassSet = ShareDataHelper.getInstance(SetAct.this).getPassSet(Constant.SHOW_PASS_SET);
		tb_pass.setChecked(showPassSet);
		
		tb_push.setOnCheckedChangeListener(this);
		tb_pass.setOnCheckedChangeListener(this);
		tb_gps.setOnCheckedChangeListener(this);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onClick(View view){
		this.finishPage();
	}
	
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@OnClick({ R.id.ly_update, R.id.ly_qiandao, R.id.ly_shiyong,
			R.id.ly_fankui, R.id.ly_fenxiang })
	public void onLayoutClick(View view){
		switch (view.getId()) {
		case R.id.ly_update:
			ToastUtil.showShort(this, "这已经是最新的版本啦");
			break;
		case R.id.ly_qiandao:
			//startActivity(new Intent(this,QiandaoAct.class));
			this.finishPage();
			break;
		case R.id.ly_shiyong:
			ToastUtil.showShort(this, "暂未开启此功能 ");
			break;
		case R.id.ly_fankui:
			//startActivity(new Intent(this,FankuiAct.class));
			this.finishPage();
			break;
		case R.id.ly_fenxiang:
			ToastUtil.showShort(this, "暂未开启此功能 ");
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.tb_push:
			if (isChecked) {
				ToastUtil.showShort(this, "推送已经开启");
			}else{
				ToastUtil.showShort(this, "推送已经关闭");
			}
			break;
		case R.id.tb_gps:
			if (isChecked) {
				ToastUtil.showShort(this, "GPS已经开启");
			}else{
				ToastUtil.showShort(this, "GPS已经关闭");
			}
			break;
		case R.id.tb_pass:
			if (isChecked) {
				ToastUtil.showShort(this, "密码锁已经开启");
				startActivity(new Intent(this,PasswordSetAct.class));
			}else{
				ToastUtil.showShort(this, "密码锁已经关闭");
			}
			ShareDataHelper.getInstance(this).savePassSet(Constant.SHOW_PASS_SET, isChecked);
			break;
		}
	}
	
	
}

