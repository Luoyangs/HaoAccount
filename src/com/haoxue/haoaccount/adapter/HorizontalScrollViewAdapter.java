package com.haoxue.haoaccount.adapter;

import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.view.TextDrawable;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 说明： 
 * 作者：Luoyangs 
 * 时间：2015-11-19
 */
public class HorizontalScrollViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Map<String, Object>> mDatas;

	public HorizontalScrollViewAdapter(Context context, List<Map<String, Object>> mDatas) {
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_type, parent, false);
			viewHolder.mImg = (ImageView) convertView.findViewById(R.id.child_img);
			//viewHolder.mText = (TextView) convertView.findViewById(R.id.child_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		int size = mDatas.size();
		String text = mDatas.get(position%size).get("text").toString();
		int index =  (Integer) mDatas.get(position%size).get("img");
		
		viewHolder.mImg.setImageDrawable(TextDrawable.builder().buildRound(text, Color.parseColor(Constant.FAVTEXTCOLOR[index%size])));
		return convertView;
	}

	private class ViewHolder {
		ImageView mImg;
		//TextView mText;
	}
}
