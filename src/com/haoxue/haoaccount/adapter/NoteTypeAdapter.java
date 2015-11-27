package com.haoxue.haoaccount.adapter;

import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.base.Constant;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-14
 */
public class NoteTypeAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;
	private int position = 0;
	private boolean islodingimg = true;
	private Holder hold;

	public NoteTypeAdapter(Context context, List<Map<String, String>> list) {
		this.context = context;
		this.list = list;
	}

	public NoteTypeAdapter(Context context, List<Map<String, String>> list, boolean islodingimg) {
		this.context = context;
		this.list = list;
		this.islodingimg = islodingimg;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int arg0, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_classify_mainlist, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		if (islodingimg == true) {
			hold.img.setImageResource(Constant.LISTVIEWIMG[arg0%10]);
		}
		hold.txt.setText(list.get(arg0).get("ptitle").toString());
		hold.txt.setTextColor(0xFF444444);
		hold.layout.setBackgroundColor(0xFFEBEBEB);
		if (arg0 == position) {
			hold.layout.setBackgroundColor(0xFFFFFFFF);
			hold.txt.setTextColor(0xFF6B9529);
		}
		return view;
	}

	public void setSelectItem(int position) {
		this.position = position;
	}

	public int getSelectItem() {
		return position;
	}

	private static class Holder {
		LinearLayout layout;
		ImageView img;
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.mainitem_txt);
			img = (ImageView) view.findViewById(R.id.mainitem_img);
			layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
		}
	}
}
