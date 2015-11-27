package com.haoxue.haoaccount.adapter;

import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-14
 */
public class NoteListAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> text_list;
	private int selectItem = -1;
	Holder hold;

	public NoteListAdapter(Context context, List<Map<String, String>> text_list) {
		this.context = context;
		this.text_list = text_list;
	}

	public int getCount() {
		return text_list.size();
	}

	public Object getItem(int position) {
		return text_list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_classify_morelist, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		hold.txt.setText(text_list.get(position).get("title"));
		hold.time.setText(text_list.get(position).get("updateTime"));
		hold.txt.setTextColor(0xFF666666);
		if (position == selectItem) {
			hold.txt.setTextColor(0xFFFF8C00);
		}
		return view;
	}

	public void setSelectItem(int position) {
		this.selectItem = position;
	}

	public void setDataLists(List<Map<String, String>> text_list){
		this.text_list = text_list;
		this.notifyDataSetChanged();
	}
	
	private static class Holder {
		TextView txt;
		TextView time;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.moreitem_txt);
			time = (TextView) view.findViewById(R.id.moreitem_time);
		}
	}
}
