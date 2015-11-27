package com.haoxue.haoaccount.act;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.handmark.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.haoxue.haoaccount.R;
import com.haoxue.haoaccount.adapter.PrepayAdapter;
import com.haoxue.haoaccount.base.AssetDBManager;
import com.haoxue.haoaccount.base.Constant;
import com.haoxue.haoaccount.base.Constant.MSG;
import com.haoxue.haoaccount.util.ToastUtil;
import com.haoxue.haoaccount.view.CuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 说明:预算
 * 作者:Luoyangs
 * 时间:2015-10-8
 */
@ContentView(R.layout.act_prepay_layout)
public class PrepayAct extends Activity {
	
	@ViewInject(R.id.pull_refresh_scrollview)
	private PullToRefreshScrollView mPullRefreshScrollView;
	@ViewInject(R.id.totalsum)
	private TextView tv_totalsum;
	@ViewInject(R.id.presum)
	private TextView tv_presum;
	@ViewInject(R.id.paysum)
	private TextView tv_paysum;
	@ViewInject(R.id.list)
	private CuListView listView;
	private SQLiteDatabase database;
	private ArrayList<Map<String, String>> list;
	private float totalsum = 0;//总预算总额
	private float paysum = 0;//已用总额
	private int year = 2015;//当前预算年份
	private int month = 1;//当前预算月份
	
	@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//停止刷新
			mPullRefreshScrollView.onRefreshComplete();
			switch (msg.what) {
			case MSG.NO_NETWORK:
				break;
			case MSG.FRESH_OK:
				ToastUtil.showShort(getBaseContext(), "刷新完成");
				break;
			case MSG.FRESH_ERROR:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化操作
		ViewUtils.inject(this);
		database = new AssetDBManager().openDatabase(this);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				if (mPullRefreshScrollView.isHeaderShown()) {
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel("下拉同步数据");
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel("放开以同步…"); 
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在同步…");
					mPullRefreshScrollView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("当前为："+ year +" 年"+ month +" 月的预算表");
					recordData();
				}
			}
		});
	}
	
	//同步数据
	private void recordData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = MSG.FRESH_OK;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//初始化归位
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		totalsum = 0;
		paysum = 0;
		list = this.getParentType();
		//显示数据
		tv_totalsum.setText(String.valueOf(totalsum));
		tv_presum.setText(String.valueOf(totalsum-paysum));
		tv_paysum.setText(String.valueOf(paysum));
		listView.setAdapter(new PrepayAdapter(PrepayAct.this, list));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(PrepayAct.this,PrepayLayerAct.class);
				intent.putExtra("pid", list.get(position).get("pid"));
				intent.putExtra("img", list.get(position).get("img"));
				intent.putExtra("name", list.get(position).get("name"));
				intent.putExtra("payout", list.get(position).get("payout"));
				intent.putExtra("num", list.get(position).get("num"));
				startActivity(intent);
			}
		});
	}
	
	//取出数据
	private ArrayList<Map<String, String>> getParentType() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Cursor cursor = database.rawQuery("select id,name,img from PTYPE_TABLE where type=3", null); 
        while(cursor.moveToNext()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("pid", "" + cursor.getInt(cursor.getColumnIndex("id")));
        	map.put("img", cursor.getString(cursor.getColumnIndex("img")));
        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
        	//判断是否存在记录
        	Cursor cursor2 = database.rawQuery(Constant.DB.HAS_PREPAY_TWO, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))}); 
        	if (cursor2.moveToFirst() && cursor2.getInt(0) == 0) {
            	map.put("payout", "0");
        		map.put("num", "0");
			}else{
				//取出数据
				Cursor cursor3 = database.rawQuery(Constant.DB.GET_PREPAY_TWO, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))}); 
				while(cursor3.moveToNext()){
					float num = cursor3.getFloat(cursor3.getColumnIndex("num"));
					float pay = cursor3.getFloat(cursor3.getColumnIndex("pay"));
					totalsum += num;
					paysum += pay;
	        		map.put("payout", String.valueOf(pay));
	        		map.put("num", String.valueOf(num));
				}
			}
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
