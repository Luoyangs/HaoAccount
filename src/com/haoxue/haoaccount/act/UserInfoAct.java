package com.haoxue.haoaccount.act;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.view.PullScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 说明：用户中心
 * 作者：Luoyangs
 * 时间：2015-10-23
 */
@ContentView(R.layout.act_userinfo_layout)
public class UserInfoAct extends Activity implements PullScrollView.OnTurnListener {

	@ViewInject(R.id.scroll_view)
	private PullScrollView mScrollView;
	@ViewInject(R.id.background_img)
    private ImageView mHeadImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
	}

	@Override
	public void onTurn() {
		
	}
	
}
