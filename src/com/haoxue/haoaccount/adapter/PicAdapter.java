package com.haoxue.haoaccount.adapter;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.Bimp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 说明：图片选择器
 * 作者：Luoyangs
 * 时间：2015-11-7
 */
public class PicAdapter extends BaseAdapter {

	private Context context;
	private int selectedPosition = -1;
	private boolean shape;

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}
	
	public PicAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		if(Bimp.tempSelectBitmap.size() == 8){
			return 8;
		}
		return (Bimp.tempSelectBitmap.size() + 1);
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_notepic_layout, null, false);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.item_grida_image);
		if (position == Bimp.tempSelectBitmap.size()) {
			image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
			if (position == 8) {
				image.setVisibility(View.GONE);
			}
		} else {
			image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
		}
		return convertView;
	}

}
