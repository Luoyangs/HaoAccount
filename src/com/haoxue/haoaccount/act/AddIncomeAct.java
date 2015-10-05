package com.haoxue.haoaccount.act;

import org.w3c.dom.Text;

import com.haoxue.haoaccount.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 说明:添加收入
 * 作者:Luoyangs
 * 时间:2015-10-5
 */
@ContentView(R.layout.act_addincom_layout)
public class AddIncomeAct extends Activity{

	@ViewInject(R.id.input)
	private EditText etNum;
	@ViewInject(R.id.tv_type)
	private TextView tvType;
	@ViewInject(R.id.tv_src)
	private TextView tvSrc;
	@ViewInject(R.id.tv_to)
	private TextView tvTo;
	@ViewInject(R.id.tv_date)
	private TextView tvDate;
	@ViewInject(R.id.etinfo)
	private EditText tvInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		finishPage();
	}
	
	@OnClick(R.id.btn_cancel)
	public void onCancel(View view){
		finishPage();
	}
	
	@OnClick(R.id.btn_save)
	public void onSave(View view){
		finishPage();
	}
	
	private void finishPage(){
		finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
}
