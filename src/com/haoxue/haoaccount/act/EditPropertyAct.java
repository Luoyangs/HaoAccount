package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

import android.app.Activity;
import android.os.Bundle;

/**
 * 说明：编辑页面
 * 作者：Luoyangs
 * 时间：2015-11-27
 */
@ContentView(R.layout.act_addincom_layout)
public class EditPropertyAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
	}
}
