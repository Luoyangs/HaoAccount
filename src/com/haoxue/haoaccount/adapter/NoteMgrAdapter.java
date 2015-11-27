package com.haoxue.haoaccount.adapter;

import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明：管理记事适配器
 * 作者：Luoyangs
 * 时间：2015-11-15
 */
public class NoteMgrAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private LayoutInflater mInflater;
	private int selectItem = -1;
	
	public NoteMgrAdapter(Context context,List<Map<String, String>> list) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void setSelectItem(int selectItem){
		this.selectItem = selectItem;
	}
	
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = mInflater.inflate(R.layout.item_imgtext_layout, null, false);
		TextView text = (TextView) view.findViewById(R.id.text);
		ImageView img = (ImageView) view.findViewById(R.id.img);
		img.setImageResource(Constant.LISTVIEWIMG[position%10]);
		text.setText(list.get(position).get("title"));
		view.setBackgroundColor(0xFFEBEBEB);
		if (position == selectItem) {
			view.setBackgroundColor(0xFFFFFFFF);
			text.setTextColor(0xFF6B9529);
		}
		return view;
	}

}
