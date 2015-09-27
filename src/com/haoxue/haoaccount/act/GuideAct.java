package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.List;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.ShareDataHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 说明:引导页
 * 作者:Luoyangs
 * 时间:2015-9-25
 */
@ContentView(R.layout.act_guide_layout)
public class GuideAct extends Activity implements OnPageChangeListener{

	@ViewInject(R.id.viewpager_guidance)
	private ViewPager viewPager;
	@ViewInject(R.id.ll)
	private LinearLayout ll;
	
	private List<View> views;
    private ImageView[] dots;
    private int currentIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		//初始化&数据加载
		initView();
	}
	
	private void initView(){
		LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.act_guide_layout_pager1, null));
        views.add(inflater.inflate(R.layout.act_guide_layout_pager2, null));
        views.add(inflater.inflate(R.layout.act_guide_layout_pager3, null));
        
        viewPager.setAdapter(new MyPagerAdapter(views));
        viewPager.setOnPageChangeListener(this);
        
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true);
	}
	

	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		setCurrentDot(position);
		if (position == views.size() - 1) {
			ShareDataHelper.getInstance(GuideAct.this).saveLoadingInfo(false);
			(new Handler()).postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if (ShareDataHelper.getInstance(GuideAct.this).getSetBoolParam(Constant.SHOW_PASS_SET)) {
						startActivity(new Intent(GuideAct.this, PasswordSetAct.class));
						finish();
					}else{
						startActivity(new Intent(GuideAct.this, MainAct.class));
						finish();
					}
				}
			}, 1200);
		}
	}
	
	private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1 || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        for (int i = 0; i < dots.length; i++) {
        	if (i == position) {
        		dots[i].setImageResource(R.drawable.dot_selected);
        		continue;
        	}
        	dots[i].setImageResource(R.drawable.dot);
        }
        currentIndex = position;
    }
	
	/**适配器*/
	private class MyPagerAdapter extends PagerAdapter{

		private List<View> list;
		
		public MyPagerAdapter(List<View> list) {
			this.list = list;
		}
		
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(list.get(arg1));
		}
		
		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPager) view).addView(list.get(position));
			return list.get(position);
		}
	}
}
