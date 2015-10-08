package com.haoxue.haoaccount.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.FileUtil;

import android.content.Context;
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
    private FileUtil util;
     
    public ListViewAdapter2(Context context, ArrayList<Map<String, String>> list) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        util = new FileUtil(context);
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
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_item2, null);
        }
        Map<String, String> map = list.get(position);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        TextView tv = (TextView)convertView.findViewById(R.id.text);
        img.setImageDrawable(util.getDrawableByName(map.get("img")));
        tv.setText(map.get("name"));
         
        return convertView;
    }
 
}
