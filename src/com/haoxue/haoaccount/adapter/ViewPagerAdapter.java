package com.haoxue.haoaccount.adapter;

import java.util.List;

import com.haoxue.haoaccount.view.CuViewPager;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * 说明：注册页面的适配器 
 * 作者：Luoyangs 
 * 时间：2015-10-25
 */
public class ViewPagerAdapter extends PagerAdapter {

	private List<View> viewLists;

	public ViewPagerAdapter(List<View> lists) {
		viewLists = lists;
	}

	@Override
	public int getCount() { // 获得size
		return viewLists.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View view, int position, Object object) {
		// 销毁Item
		((CuViewPager) view).removeView(viewLists.get(position));
	}

	@Override
	public Object instantiateItem(View view, int position) {
		// 实例化Item
		((CuViewPager) view).addView(viewLists.get(position), 0);
		return viewLists.get(position);
	}

}
