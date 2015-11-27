package com.haoxue.haoaccount.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 说明：借贷
 * 作者：Luoyangs
 * 时间：2015-11-27
 */
@ContentView(R.layout.act_addincom_layout)
public class AddLendAct extends BaseActivity implements OnClickListener{

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar;
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
		//添加事件监听
		tvDate.setOnClickListener(this);
		tvType.setOnClickListener(this);
		tvSrc.setOnClickListener(this);
		tvTo.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_type:
			
			break;
		case R.id.tv_src:
			
			break;
		case R.id.tv_to:
			
			break;
		case R.id.tv_date:
			initDateTime(view);
			break;
		}
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
		
	}
}
