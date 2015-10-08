package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.ListViewAdapter3;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.view.CuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 说明:预算
 * 作者:Luoyangs
 * 时间:2015-10-8
 */
@ContentView(R.layout.act_prepay_layout)
public class PrepayAct extends Activity {

	@ViewInject(R.id.list)
	private CuListView listView;
	private SQLiteDatabase database;
	private ArrayList<Map<String, String>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		list = this.getParentType();
		listView.setAdapter(new ListViewAdapter3(PrepayAct.this, list));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(PrepayAct.this,PrepayLayerAct.class);
				intent.putExtra("img", list.get(position).get("img"));
				intent.putExtra("name", list.get(position).get("name"));
				intent.putExtra("payout", list.get(position).get("payout"));
				intent.putExtra("num", list.get(position).get("num"));
				startActivity(intent);
			}
		});
	}
	
	private ArrayList<Map<String, String>> getParentType() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery("select name,img from PTYPE_TABLE where type=3", null); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	map.put("payout", String.valueOf(0));
        	map.put("num", String.valueOf(100));
        	list.add(map);
        }
        return list;
    }
	
	@OnClick(R.id.titilbar_left)
	public void onBack(View view){
		this.finish();
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
	}
}
