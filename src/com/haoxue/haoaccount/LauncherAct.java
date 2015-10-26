package com.haoxue.haoaccount;

import com.haoxue.haoaccount.act.GuideAct;
import com.haoxue.haoaccount.act.MainAct;
import com.haoxue.haoaccount.act.PasswordSetAct;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

/**
 * 
 * 说明:启动页
 * 作者:Luoyangs
 * 时间:2015-9-25
 */
@ContentView(R.layout.act_launcher_layout)
public class LauncherAct extends Activity {

	private boolean isFirstIn;
	@ViewInject(R.id.start)
	private ImageView start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		//动画渐变
		startAnimation();
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void startAnimation(){
		//图片渐变模糊度始终  
        AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);  
        //渐变时间  
        animation.setDuration(3600);  
        //展示图片渐变动画  
        start.setAnimation(animation);
        start.postOnAnimationDelayed(new Runnable() {
			
			@Override
			public void run() {
				isFirstIn = ShareDataHelper.getInstance(LauncherAct.this).getLoadingInfo();
				boolean showPassSet = ShareDataHelper.getInstance(LauncherAct.this).getPassSet(Constant.SHOW_PASS_SET);
				if (isFirstIn) {
					startActivity(new Intent(LauncherAct.this,GuideAct.class));
					overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
					finish();
				}else{
					if (showPassSet) {
						startActivity(new Intent(LauncherAct.this,PasswordSetAct.class));
						overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
						finish();
					}else{
						startActivity(new Intent(LauncherAct.this,MainAct.class));
						overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
						finish();
					}
				}
			}
		}, 3600);
	}

}
