package com.haoxue.haoaccount.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.FileUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
public class ListViewAdapter2 extends BaseAdapter{
    
    private LayoutInflater inflater;
    private ArrayList<Map<String, String>> list;
    private Context context;
    private int selectIndex = -1;
     
    public ListViewAdapter2(Context context, ArrayList<Map<String, String>> list) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }
 
    @Override
    public int getCount() {
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
        return null;
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_item2, null);
        }
        Map<String, String> map = list.get(position);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        TextView tv = (TextView)convertView.findViewById(R.id.text);
        img.setImageDrawable(FileUtil.getDrawableByName(context,map.get("img")));
        tv.setText(map.get("name"));
        if (position == selectIndex) {
        	tv.setTextColor(Color.parseColor("#E07E05"));
		}else{
			tv.setTextColor(Color.parseColor("#aaaaaa"));
		}
        return convertView;
    }
 
}
