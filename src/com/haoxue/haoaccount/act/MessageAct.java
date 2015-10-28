package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

import android.app.Activity;
import android.os.Bundle;

/**
 * 说明：消息列表
 * 作者：Luoyangs
 * 时间：2015-10-28
 */
@ContentView(R.layout.act_message_layout)
public class MessageAct extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
	}
}
