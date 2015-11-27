package com.haoxue.haoaccount.adapter;

import java.text.DecimalFormat;
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
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 说明:预算
 * 作者:Luoyangs
 * 时间:2015-10-6
 */
public class PrepayAdapter extends BaseAdapter{
    
    private LayoutInflater inflater;
    private ArrayList<Map<String, String>> list;
    private Context context;
     
    public PrepayAdapter(Context context, ArrayList<Map<String, String>> list) {
        super();
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_prepay_layout, null);
        }
        Map<String, String> map = list.get(position);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);
        TextView type = (TextView)convertView.findViewById(R.id.type);
        TextView payout = (TextView)convertView.findViewById(R.id.pay);
        TextView prepay = (TextView)convertView.findViewById(R.id.pre);
        ProgressBar bar = (ProgressBar)convertView.findViewById(R.id.num);
        img.setImageDrawable(FileUtil.getDrawableByName(context,map.get("img")));
        type.setText(map.get("name"));
        payout.setText("已用 "+ new DecimalFormat("0.00").format(Float.parseFloat(map.get("payout"))));
        prepay.setText("预算 "+ new DecimalFormat("0.00").format(Float.parseFloat(map.get("num"))));
        bar.setMax((int)Float.parseFloat(map.get("num")));
        bar.setProgress((int)Float.parseFloat(map.get("payout")));
        if (((int)Float.parseFloat(map.get("payout"))) >0 || ((int)Float.parseFloat(map.get("num"))) >0) {
        	bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_color));
		}else{
			bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar_base));
		}
        
        return convertView;
    }
 
}
