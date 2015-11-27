package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.HorizontalScrollViewAdapter;
import com.haoxue.haoaccount.adapter.PersonTypeAdapter;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.view.CuHorizontalScrollView;
import com.haoxue.haoaccount.view.CuHorizontalScrollView.CurrentImageChangeListener;
import com.haoxue.haoaccount.view.CuHorizontalScrollView.OnItemClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 说明： 更多记账分类
 * 作者：Luoyangs 
 * 时间：2015-11-19
 */
@ContentView(R.layout.act_moretype_layout)
public class MoreTypeAct extends Activity {

	@ViewInject(R.id.myscroll)
	private CuHorizontalScrollView scrollView;
	@ViewInject(R.id.listview)
	private ListView listview;
	private HorizontalScrollViewAdapter adapter;
	private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	private List<String> personlist = new ArrayList<String>();
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		// 新增子布局对象到父布局中
		database = new AssetDBManager().openDatabase(this);
		loadData();
		loadPersonData();
		adapter = new HorizontalScrollViewAdapter(this, list);  
	    scrollView.setOnItemClickListener(new OnItemClickListener(){  
	  
	        @Override  
	        public void onClick(View view, int position){  
	            view.setBackgroundColor(Color.parseColor("#ff623405"));
	            ((TextView)view.findViewById(R.id.child_name)).setTextColor(Color.parseColor("#6B9529"));
	            String type = list.get(position).get("text").toString();
	            if (type.equals("收入")) {
					startActivity(new Intent(MoreTypeAct.this,AddIncomeAct.class));
				}else if (type.equals("支出")) {
					startActivity(new Intent(MoreTypeAct.this,AddOutcomeAct.class));
				}else if (type.equals("预算")) {
					startActivity(new Intent(MoreTypeAct.this,PrepayAct.class));
				}else if (type.equals("借贷")) {
					///startActivity(new Intent(MoreTypeAct.this,AddIncomeAct.class));
				}else if (type.equals("报销")) {
					///startActivity(new Intent(MoreTypeAct.this,AddIncomeAct.class));
				}else if (type.equals("转账")) {
					startActivity(new Intent(MoreTypeAct.this,AddTransferAct.class));
				}
	        }  
	    });
	    scrollView.setCurrentImageChangeListener(new CurrentImageChangeListener() {
			
			@Override
			public void onCurrentImgChanged(int position, View view) {
	            view.setBackgroundColor(Color.parseColor("#ff623405"));
	            ((TextView)view.findViewById(R.id.child_name)).setTextColor(Color.parseColor("#6B9529"));
			}
		});
	    //设置适配器  
	    scrollView.initDatas(adapter);  
	    listview.setAdapter(new PersonTypeAdapter(this, personlist));
	}
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
	
	private void loadData(){
		list.clear();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("img", 0);
		map.put("text", "收入");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img", 1);
		map.put("text", "支出");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img", 2);
		map.put("text", "预算");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img", 3);
		map.put("text", "借贷");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img", 4);
		map.put("text", "报销");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img", 5);
		map.put("text", "转账");
		list.add(map);
	}
	
	private void loadPersonData(){
		Cursor cursor = database.rawQuery(Constant.DB.GET_TYPE, null); 
    	if (cursor.getCount() == 0) {
    		return;
		}else{
			//取出数据
			while(cursor.moveToNext()){
        		personlist.add(cursor.getString(cursor.getColumnIndex("name")));
			}
		}
	}
}
