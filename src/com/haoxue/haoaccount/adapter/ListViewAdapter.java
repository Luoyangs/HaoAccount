package com.haoxue.haoaccount.adapter;

import java.util.List;

import com.haoxue.haoaccount.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
public class ListViewAdapter extends BaseAdapter{
    
    private LayoutInflater inflater;
    private List<String> list; 
     
    public ListViewAdapter(Context context, List<String> list) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.list = list;
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
            convertView = inflater.inflate(R.layout.lv_item, null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.text);
        tv.setText(list.get(position));
         
        return convertView;
    }
 
}
