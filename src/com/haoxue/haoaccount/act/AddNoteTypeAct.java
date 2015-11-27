package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-14
 */
@ContentView(R.layout.act_additem_layout)
public class AddNoteTypeAct extends Activity {

	@ViewInject(R.id.add_text)
	private EditText add_text;
	@ViewInject(R.id.titilbar_title)
	private TextView titilbar_title;
	@ViewInject(R.id.add_tip)
	private TextView add_tip;
	private String type = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		type = getIntent().getStringExtra("type");
		if (type.equals("addtype")) {
			titilbar_title.setText("新增类别");
			add_tip.setText("类别名称");
		}else if (type.equals("edit")) {
			titilbar_title.setText("编辑类别");
			add_tip.setText("类别名称");
		}
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	@OnClick(R.id.btnok)
	public void onPost(View view){

	}
}
