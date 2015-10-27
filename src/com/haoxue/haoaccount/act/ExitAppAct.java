package com.haoxue.haoaccount.act;

import com.ant.liao.GifView;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.ActivityManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 说明：退出系统
 * 作者：Luoyangs
 * 时间：2015-10-27
 */
@ContentView(R.layout.act_finish_layout)
public class ExitAppAct extends Activity {

	private int i;
	boolean doing = true;
	@ViewInject(R.id.gif_bye)
	private GifView gif;
	@ViewInject(R.id.iv_off)
	private ImageView iv_off;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 3) {
				ObjectAnimator.ofFloat(gif, "alpha", 1.0f, 0.0f).setDuration(1800).start();
			}
			if (msg.what == 5) {
				Animation tv_off = AnimationUtils.loadAnimation(ExitAppAct.this, R.anim.tv_off);
				iv_off.startAnimation(tv_off);
				tv_off.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						iv_off.setVisibility(View.GONE);
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {}
					
					@Override
					public void onAnimationEnd(Animation animation) {}
					
				});				
			}
			if (msg.what == 6) {
				gif.setVisibility(View.GONE);
			}
			if (msg.what == 8) {
				ActivityManager.getInstance().exit();
				System.gc();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		gif.setGifImage(R.drawable.bye);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (doing) {
					try {
						Thread.sleep(500);
						i++;
						Message msg = new Message();
						msg.what = i;
						handler.sendMessage(msg);
						if (i == 9) doing = false;
					} catch (Exception e) {}
				}
			}
		}).start();
	}
}
