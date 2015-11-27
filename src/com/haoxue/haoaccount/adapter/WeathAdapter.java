package com.haoxue.haoaccount.adapter;

import java.util.Calendar;
import java.util.List;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.bean.WeatherSubBean;
import com.haoxue.haoaccount.view.HXImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 说明：
 * 作者：Luoyangs
 * 时间：2015-11-15
 */
public class WeathAdapter extends BaseAdapter {

	private Context context;
	private List<WeatherSubBean> list;
	
	public WeathAdapter(Context context,List<WeatherSubBean> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
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
		View view = LayoutInflater.from(context).inflate(R.layout.item_gridweather_layout, null,false);
		HXImageView image = (HXImageView) view.findViewById(R.id.img);
		TextView temp = (TextView) view.findViewById(R.id.temptext);
		TextView time = (TextView) view.findViewById(R.id.timetext);
		//判断是白天还是晚上
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); 
		if (hour >17 || hour < 7) {//晚上
			image.loadImage(list.get(position).getNightPictureUrl());
		}else{
			image.loadImage(list.get(position).getDayPictureUrl());
		}
		temp.setText(list.get(position).getTemperature());
		if (position == 0) {
			time.setText(list.get(position).getDate().subSequence(0, 2));
		}else{
			time.setText(list.get(position).getDate());
		}
		return view;
	}

}
