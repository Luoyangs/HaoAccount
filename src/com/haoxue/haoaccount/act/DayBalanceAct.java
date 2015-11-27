package com.haoxue.haoaccount.act;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.util.FileUtil;
import com.haoxue.haoaccount.view.CuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 说明:日结余
 * 作者:Luoyangs
 * 时间:2015-10-11
 */
@ContentView(R.layout.act_balance_layout)
public class DayBalanceAct extends Activity {

	@ViewInject(R.id.titilbar_title)
	private TextView titilbar_title;
	@ViewInject(R.id.day_balance)
	private TextView day_balance;
	@ViewInject(R.id.day_in)
	private TextView day_in;
	@ViewInject(R.id.day_out)
	private TextView day_out;
	@ViewInject(R.id.list)
	private CuListView listview;
	
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		myAdapter = new MyAdapter(this, getArrayList());
		listview.setAdapter(myAdapter);
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finishPage();
	}
		
	private void finishPage(){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private ArrayList<Map<String, String>> getArrayList(){
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 7; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("img", "j"+i);
			map.put("name", "知识点"+i);
			map.put("num", String.valueOf(new Random().nextInt(200)));
			list.add(map);
		}
		return list;
	}
	
	private class MyAdapter extends BaseAdapter{

		private LayoutInflater inflater;
	    private ArrayList<Map<String, String>> list;
		
		public MyAdapter(Context context,ArrayList<Map<String, String>> list){
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
	    	Map<String, String> map = list.get(position);
	    	if (position == 0) {
	        	if (convertView == null) {
		            convertView = inflater.inflate(R.layout.item_balance_top_layout, null);
		        }
			}else if (position == list.size() - 1){
				if (convertView == null) {
		            convertView = inflater.inflate(R.layout.item_balance_bom_layout, null);
		        }
			}else{
				if (convertView == null) {
		            convertView = inflater.inflate(R.layout.item_balance_mid_layout, null);
		        }
			}
	        
	        ImageView img = (ImageView)convertView.findViewById(R.id.img);
	        TextView name = (TextView)convertView.findViewById(R.id.name);
	        TextView num = (TextView)convertView.findViewById(R.id.num);
	        img.setImageDrawable(FileUtil.getDrawableByName(getBaseContext(),map.get("img")));
	        name.setText(map.get("name"));
	        num.setText(""+ new DecimalFormat(".00").format(Float.parseFloat(map.get("num"))));
	        
	        return convertView;
	    }
	}
}
